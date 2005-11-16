

The eMayorClient installer contains the following
applications:


1) eMayorClientInstaller.exe
----------------------------

This is a native installer for Windows 2000 and XP.

What it does:
-------------

- It checks the available Java Runtime
Environments (JRE) installed on the client computer
and lets the user start the installation
of the required JRE 1.5+ if its required.

-It checks the JRE which is currently used by
the installed browsers and if required
changes these settings so that the browsers
will used the JRE1.5+ required by the
eMayorForms client applet.

-It starts the emayorFormsClientInstaller.jar
java installer, which extracts the
required installation files from
itself and copies them to the
required locations on the client 
computer.

The java installer eMayorClientInstaller.jar is
contained in the native installer, so
if you use the native installer,
it don't need to start the java installer too.

Note: The native installer accesses the
Windows Registry. It may need administrator
right. However, it will tell you automatically,
if there are problems.



2) eMayorClientInstaller.jar
----------------------------

This is the java installer, which
is called from the native installer.

It should work for all operating systems.

If you only used this installer,
you must do the following things
before starting this one:

- Install a JRE 1.5+
- Set this JRE to be used by the internet browsers.


Note: The complete sources are contained in
eMayorClientInstaller.jar too.


Version 1
18.Oct. 2005
J.Plaz, UoZurich


[don't remove / keep UTF-8 by this: äöüàéè]
