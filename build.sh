#!/bin/bash

## resolve folder of this script, following all symlinks,
## http://stackoverflow.com/questions/59895/can-a-bash-script-tell-what-directory-its-stored-in
SCRIPT_SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SCRIPT_SOURCE" ]; do # resolve $SOURCE until the file is no longer a symlink
  SCRIPT_DIR="$( cd -P "$( dirname "$SCRIPT_SOURCE" )" && pwd )"
  SCRIPT_SOURCE="$(readlink "$SCRIPT_SOURCE")"
  # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
  [[ $SCRIPT_SOURCE != /* ]] && SCRIPT_SOURCE="$SCRIPT_DIR/$SCRIPT_SOURCE"
done
readonly SCRIPT_DIR="$( cd -P "$( dirname "$SCRIPT_SOURCE" )" && pwd )"

set -ex

byteman="http://byteman.jboss.org/docs.html"
byteman_guide="http://downloads.jboss.org/byteman/4.0.5/byteman-programmers-guide.html#trace-and-debug-operations"
byteman_tutorial="https://developer.jboss.org/wiki/ABytemanTutorial#top"

name=EthernalCrashes

if [ -d "$JAVA_HOME" ] ; then
  ls -l "$JAVA_HOME"/bin/javac
else
  JAVA_HOME=$( dirname $( dirname $(readlink -f $(which javac))))
fi


#Choice   Option                 Information generated in class
#     1   -g:none                No debug information
#     2   -g:lines               Line number only
#     3   -g:lines,source        Line number & source file
#     4   (default)              Same as #3
#     5   -g:lines,source,vars   Line number, source file & variables
#     6   -g                     Same as #5


rm -rf $SCRIPT_DIR/cdist
mkdir  $SCRIPT_DIR/cdist
pushd  $SCRIPT_DIR/cdist

  echo "Main-Class: zzlast.EthernalCrashes
  " > "Manifest.txt"


  classes=`find ../src -type f`
  release=8

  mkdir    fulldebugBuild
  $JAVA_HOME/bin/javac --release $release -d fulldebugBuild -g:lines,source,vars $classes
  pushd    fulldebugBuild
    $JAVA_HOME/bin/jar -cmf ../Manifest.txt $name.jar *
  popd

  mkdir    nodebugBuild
  $JAVA_HOME/bin/javac --release $release -d nodebugBuild -g:none $classes
  pushd    nodebugBuild
    $JAVA_HOME/bin/jar -cmf ../Manifest.txt  $name.jar *
  popd

  rm -v Manifest.txt

  pushd $SCRIPT_DIR
    obfus="proguard"
    obfus_version="7.1.0-beta5" # max jdk (above `release`) 16
    obfus_tar="$obfus-$obfus_version.zip"
    #https://downloads.sourceforge.net/project/proguard/v7.1.0-beta5/proguard-7.1.0-beta5.zip
    obfus_page="https://downloads.sourceforge.net/project/$obfus/v$obfus_version/$obfus_tar"
    obfus_dir=$SCRIPT_DIR/$obfus-$obfus_version
    obfus_jar="$obfus_dir/lib/$obfus.jar"
    if [ ! -f "$obfus_jar" ] ; then
       if [ ! -f "$obfus_tar" ] ; then
         wget "$obfus_page"
       fi
       if [ ! -d "$obfus_dir" ] ; then
         unzip $obfus_tar
       fi
    fi
    ls -l $obfus_jar
  popd

  # jdk8
  #libraryjars="-libraryjars  $JAVA_HOME/lib/rt.jar"
  # modular jdks; dnf install java-XY-openjdk-jmods!
  libraryjars="-libraryjars $JAVA_HOME/jmods/java.base.jmod(!**.jar;!module-info.class)"

  mkdir   fulldebugObfuscated
  java -jar $obfus_jar \
    -injars       fulldebugBuild/$name.jar \
    -outjars      fulldebugObfuscated/$name.jar \
     $libraryjars \
    -printmapping fulldebugObfuscated/$name.map  \
    -optimizationpasses 3  \
    -overloadaggressively  \
    -keep "public class zzlast.EthernalCrashes { 
      public static void main(java.lang.String[]); 
     }" \
    -keep "public interface zzlast.helpers.Proceedable { 
         *; 
     }" \
    -keep "public interface es.IServer { 
         *; 
     }" \
    -keep "public class ex.NetworkCalc { 
        public int add(int , int );
        public int sub(int , int );
        public int mul(int , int );
        public int div(int , int );
     }"

  mkdir   nodebugObfuscated
    java -jar $obfus_jar \
    -injars       nodebugBuild/$name.jar \
    -outjars      nodebugObfuscated/$name.jar \
     $libraryjars \
    -printmapping nodebugObfuscated/$name.map  \
    -optimizationpasses 3  \
    -overloadaggressively  \
    -keep "public class zzlast.EthernalCrashes { 
      public static void main(java.lang.String[]); 
     }" \
    -keep "public interface zzlast.helpers.Proceedable { 
         *; 
     }" \
    -keep "public interface es.IServer { 
         *; 
     }" \
    -keep "public class ex.NetworkCalc { 
        public int add(int , int );
        public int sub(int , int );
        public int mul(int , int );
        public int div(int , int );
     }"

popd
