USAGE:

! Edit build.xml and change the properties of the process name and namespace.

README:

Replaces every string that matches 
"http://localhost:9700/orabpel/${oldval}"
where ${oldval} is the current BPEL-Domain, with
"http://localhost:9700/orabpel/${deploy}"
where ${deploy} is the BPEL-Domain specified in eMayor-BPEL/build.xml

--- WILL EDIT ONLY "bpel.xml" FILES IN ROOT OF PROCESSES ---

