# Verityco: The Akka Actor Checking Tool #

Authors:
 - Emin Topalovic <emint@cs.stanford.edu>
 - Brennan Saeta  <saeta@cs.stanford.edu>

Verityco is a runtime tool that helps detect bad actor code.


# Developing #

Verityco is an `sbt` based project. To set yourself up, please install `sbt`.
Once installed, you can clone the repository and build the project. To set up
Eclipse (our favorite IDE), run `sbt eclipse` on the command line. Then import
the project in Eclipse: File > Import... > Existing Projects into Workspace

> Note: Do NOT check in the `.project` and `.classpath` Eclipse configuration
> files.

## Extensions ##

Things to do:
 - While we capture the constructor, and the receive functions as called by 
   the Akka actors API, we do not capture calls made to preStart, postStop
   or other life-cycle events. These should be instrumented as well with the
   thread state actor.