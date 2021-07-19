jar_path=build/outputs/apk-channel.jar

apk= $2

if [ '-r' == $1 ]; then
  java -cp $jar_path com.sumauto.ChannelTool -r $2
fi

if [ '-w' == $1 ]; then
  channels=$3
  java -cp $jar_path com.sumauto.ChannelTool -w $2 $3
fi
