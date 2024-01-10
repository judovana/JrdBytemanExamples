# JrdBytemanExamples
Examples to usage of Java-runtime-decompiler and byteman

main resources:
 * http://byteman.jboss.org/docs.html
 * http://downloads.jboss.org/byteman/4.0.5/byteman-programmers-guide.html
 * https://developer.jboss.org/wiki/ABytemanTutorial#top

workshop reuirements
 * dnf install java-runtime-decompiler byteman
 * tar -xf cdist.tar.xz (download from releases of this project)

Content
 * jrd introduction - https://github.com/pmikova/Java-Runtime-Decompiler/
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
 * thread/dead 
   * synchronization info
 * row
   * misleading radnom failure
   * nasty memory leak
 * server
   *  bad impl
   *  replace by custom
