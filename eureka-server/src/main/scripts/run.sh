#!/bin/sh

JAVA_HOME="$JAVA_HOME"


RUNNING_USER=root
if [ -z $APP_HOME ]; then
    APP_HOME=$(cd "../"; pwd)
fi

APP_MAINCLASS=net.itxiu.eureka.server.EurekaServerApplication

CLASSPATH=$APP_HOME/config
for i in "$APP_HOME"/lib/*.jar; do
   CLASSPATH="$CLASSPATH":"$i"
done

mkdir -p $APP_HOME/log

JAVA_OPTS="-server -Xms${heap.size} -Xmx${heap.size} -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=128M -XX:+UseFastAccessorMethods -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

if [ ! -z $2 ]; then
    JAVA_OPTS=$JAVA_OPTS" "$2
fi

psid=0

checkpid() {
   javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAINCLASS`

   if [ -n "$javaps" ]; then
      psid=`echo $javaps | awk '{print $1}'`
   else
      psid=0
   fi
}


start() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo "================================"
      echo "warn: $APP_MAINCLASS already started! (pid=$psid)"
      echo "================================"
   else
      echo -n "Starting $APP_MAINCLASS ..."
      nohup $JAVA_HOME/bin/java $JAVA_OPTS -classpath $CLASSPATH $APP_MAINCLASS >$APP_HOME/log/console.log 2>&1 &
      checkpid
      if [ $psid -ne 0 ]; then
         echo "(pid=$psid) [OK]"
      else
         echo "[Failed]"
      fi
   fi
}


fork(){

  for i in {1..3}; do
    echo -n "Starting $APP_MAINCLASS ... instance $i"
    nohup $JAVA_HOME/bin/java $JAVA_OPTS -Dspring.profiles.active=peer$i -classpath $CLASSPATH $APP_MAINCLASS >$APP_HOME/log/console_peer$i.log 2>&1 &
  done

}

stopall(){

    echo `$JAVA_HOME/bin/jps -l | grep $APP_MAINCLASS`

    $JAVA_HOME/bin/jps -l | grep $APP_MAINCLASS | awk '{print $1}' | xargs kill

}

stop() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo -n "Stopping $APP_MAINCLASS ...(pid=$psid) "
      kill $psid
      if [ $? -eq 0 ]; then
         echo "[OK]"
      else
         echo "[Failed]"
      fi

      checkpid
      if [ $psid -ne 0 ]; then
         stop
      fi
   else
      echo "================================"
      echo "warn: $APP_MAINCLASS is not running"
      echo "================================"
   fi
}


status() {
   checkpid

   if [ $psid -ne 0 ];  then
      echo "$APP_MAINCLASS is running! (pid=$psid)"
   else
      echo "$APP_MAINCLASS is not running"
   fi
}


info() {
   echo "System Information:"
   echo "****************************"
   echo `head -n 1 /etc/issue`
   echo `uname -a`
   echo
   echo "JAVA_HOME=$JAVA_HOME"
   echo `$JAVA_HOME/bin/java -version`
   echo
   echo "APP_HOME=$APP_HOME"
   echo "APP_MAINCLASS=$APP_MAINCLASS"
   echo "****************************"
}


case "$1" in
    'start')
      start
      ;;
    'stop')
     stop
     ;;
    'restart')
     stop
     start
     ;;
    'status')
     status
     ;;
    'info')
     info
     ;;
    'fork')
      fork
      ;;
    'stopall')
      stopall
      ;;
 *)
     echo "Usage: $0 {start|stop|restart|status|info}"
     exit 1
  esac