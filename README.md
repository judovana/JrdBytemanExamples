# runtime  h-o-t-f-i-x  game
If you build this repository - `build.sh`, currently only **jdk11** as build jdk is suported due to obfuscator of proguard being very fragile by build jdk - it will create four directories in `cdist` for you:
```
easy: fulldebugBuild
medium: nodebugBuild
more medium: fulldebugObfuscated
hard: nodebugObfuscated
```
In each of them is jar which is normal, or with missing debuginfo or obfuscated or both. Note, that some obfuscated tasks cannot be fixed due to optimalizations proguard did. So be aware!
```
$ java -jar cdist/fulldebugBuild/EthernalCrashes.jar 
date1 okl math uniq row dead server
```

Your task, if you choose to accept is, is to run the EthernalCrashes.jar - **wihtout looking to sources** and use static reverse engineering or runtime inspection/modification tols to fix this.

Originally, the tasks (`date1 okl math uniq row dead server`) were supposed to be investigated by [java-runtime-(de)compiler](https://github.com/judovana/Java-Runtime-Decompiler/) which was only **decompiler** in that time, so provided read only access to running jvm. And investiated and fixed by [byteman](http://byteman.jboss.org/docs.html). All the solutions for byteman injections are [included](https://github.com/judovana/JrdBytemanExamples/tree/master/btmn). If you will be able to write, and understanf, those byteman scripts, you will be true byteman manster.

In those times, when [java-runtime-(de)compiler](https://github.com/judovana/Java-Runtime-Decompiler/)  can also compile code back to running VM, the task is much more simple, and you can avoid byteman completly.

## tasks
I had wrote this only from top of my head, please let me know if it is wrong:
```
java -jar cdist/fulldebugBuild/EthernalCrashes.jar  <id>
```
where id is one of:
 * date1 -  date is not printed, investiagte why and fix
 * okl - obviusly malfuncting, tell why and fix
 * math - calculator do not compute properly, fix
 * uniq - obviusly malfuncting, tell why and fix
 * row - this is  memory leak, fix (before time run out. Note, you can get more time by extending heap O:)
 * dead - this should be deadlock, fix (before deadlock happens) (afaik requires init of classes). IIRC, `synchronization`  keyword can not be changed in runtimee. In runtime, you can workaround it. But real fix must be done in the filesystem (in jar itself) before start. JRD can do this too. In this case, byteman can not fix (iirc) but can provide all necessary info to pinpoint the issue
 * server - this is similar to date, okl and math. The assigmet is extended: Your servrer is printing out messages, and it should be printinf stories. However the whole impl is to complex, you have to **add* new implementation of IServer, and replace current implementation creation by creation of your new implementation (boith jrd and byteman supports upload of new classes)

# Original useless readme
Examples to usage of Java-runtime-decompiler and byteman

main resources:
 * https://github.com/judovana/Java-Runtime-Decompiler/**
 * http://byteman.jboss.org/docs.html
 * http://downloads.jboss.org/byteman/4.0.5/byteman-programmers-guide.html
 * https://developer.jboss.org/wiki/ABytemanTutorial#top

workshop reuirements
 * dnf install java-runtime-decompiler byteman
 * tar -xf cdist.tar.xz (download from releases of this project)

Content
 * jrd introduction - https://github.com/judovana/Java-Runtime-Decompiler/
   * gui, tui, javap, jasm, byteman
   * fernflower x procyon x asm x byteman
   * back-compilation, search...
 * byteman limitations
   * for variable rules, depends on debug table; still have parameters access
   * method must be reloaded before actions takes effect, so no direct effect to while(true){}, but ok with any subcalls (unless it got inlined)
 * surprising strengths are in manipulating ALL overriding/implementing methods
 * bm \<tab\>
   * https://developer.jboss.org/wiki/ABytemanTutorial#how_do_i_load_rules_into_a_running_program + https://developer.jboss.org/wiki/ABytemanTutorial#how_do_i_install_the_agent_into_a_running_program
   * bminstall pid
   * bminstall \`java-runtime-decompiler  -listjvms | grep Ethernal | sed "s/ .*//"\`
   * bmsubmit file
     * no error reporting!
     * to vverify rules ru bmcheck
   * bmcheck, useless without -cp :(
     * worthy to run  before every submit
   * bmsubmit -u file
   * bmsubmit can redefine rules, but be aware once you remove rule form file. So better to -u them always
  
 * jrd + byteman
   * no overwhelming substitution
   * good addition
   * great for apps without debuginfo and/or obfuscated
     * ITW case
 * http://downloads.jboss.org/byteman/4.0.5/byteman-programmers-guide.html#location-specifiers + http://downloads.jboss.org/byteman/4.0.5/byteman-programmers-guide.html#rule-expressions
 
 # examples
 Each btms/\*.btm file have in header command (eg. jaav -jar EtehrnalCrashes.jar date1) which launces exemplar case.
 You can then connect to the running example, study it and fixit. Each .btm file contains also rules which ccen be injected inside and which add additional stdouts or traces. As bonus, where possible, there are rules whch can fix the bugs in th running applications on the fly. 
 
 I would recommend to bmsubmit and -u rules one by one, and then in combinations, otherwise it will be hard for you to sctudy individual rules.
 
 * date
   * full debuginfo
   * simple info obtaining
   * simple injections and fixes
 * calc
   * no debug info
   * binding
   * manipulating results
 * uniq
   * discovering obfuscated code
   * flags, countdowns
   * fixing essential methods
 * dead 
   * synchronization info
 * row
   * misleading radnom failure
   * nasty memory leak
 * server
   *  bad impl
   *  replace by custom
