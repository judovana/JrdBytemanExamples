#!/bin/bash
set -ex
byteman="http://byteman.jboss.org/docs.html"
byteman_guide="http://downloads.jboss.org/byteman/4.0.5/byteman-programmers-guide.html#trace-and-debug-operations"
byteman_tutorial="https://developer.jboss.org/wiki/ABytemanTutorial#top"

name=EthernalCrashes

#Choice   Option                 Information generated in class
#     1   -g:none                No debug information
#     2   -g:lines               Line number only
#     3   -g:lines,source        Line number & source file
#     4   (default)              Same as #3
#     5   -g:lines,source,vars   Line number, source file & variables
#     6   -g                     Same as #5


rm -rf cdist
mkdir  cdist
pushd  cdist

  echo "Main-Class: zzlast.EthernalCrashes
  " > "Manifest.txt"


  classes=`find ../src -type f`

  mkdir    niceBuild
  javac -d niceBuild -g:lines,source,vars $classes
  pushd    niceBuild
    jar -cmf ../Manifest.txt $name.jar *
  popd

  mkdir    badBuild
  javac -d badBuild -g:none $classes
  pushd    badBuild
    jar -cmf ../Manifest.txt  $name.jar *
  popd

  rm -v Manifest.txt

obfus="proguard"
obfus_page="https://sourceforge.net/projects/proguard/"
obfus_version="6.0.3"
obfus_tar="/$HOME/Downloads/proguard"$obfus_version".tar.gz"
obfus_jar="proguard.jar"
obfus_jar_path="../$obfus_jar"

  if [ ! -f $obfus_jar_path ] ; then
    tar -xvf $obfus_tar $obfus$obfus_version/lib/$obfus_jar
     mv $obfus$obfus_version/lib/$obfus_jar $obfus_jar_path
     rm -rvf $obfus$obfus_version
  fi

  mkdir   obfuscatedNiceBuild
  java -jar $obfus_jar_path \
    -injars       niceBuild/$name.jar \
    -outjars      obfuscatedNiceBuild/$name.jar \
    -libraryjars  $( dirname $( dirname $(readlink -f $(which java))))/lib/rt.jar \
    -printmapping obfuscatedNiceBuild/$name.map  \
    -optimizationpasses 3  \
    -overloadaggressively  \
    -keep "public class zzlast.EthernalCrashes { 
      public static void main(java.lang.String[]); 
     }" \
    -keep "public class ex.NetworkCalc { 
        public int add(int , int );
        public int sub(int , int );
        public int mul(int , int );
        public int div(int , int );
     }"

  mkdir   obfuscatedBadBuild
    java -jar $obfus_jar_path \
    -injars       badBuild/$name.jar \
    -outjars      obfuscatedBadBuild/$name.jar \
    -libraryjars  $( dirname $( dirname $(readlink -f $(which java))))/lib/rt.jar \
    -printmapping obfuscatedBadBuild/$name.map  \
    -optimizationpasses 3  \
    -overloadaggressively  \
    -keep "public class zzlast.EthernalCrashes { 
      public static void main(java.lang.String[]); 
     }" \
    -keep "public class ex.NetworkCalc { 
        public int add(int , int );
        public int sub(int , int );
        public int mul(int , int );
        public int div(int , int );
     }"

popd
