## 1. Introduction

Ns-allinone is a package which contains required components and some of
optional components used in running ns. The package contains an
"install" script to automatically configure, compile and install these
components. If you haven't installed ns before and want to quickly try
ns out, ns-allinone may be easier than getting all the pieces by hand.
 
## 2. FEATURES IN ns-allinone-2.35

#### Features in this version include:

> Update to Tcl/Tk 8.5 series (becoming the default Tcl/Tk version on
  many platforms)
  
>  Update for Cygwin-1.7.1 release for Windows
  
>  New features for ns-2.35 release (see the ns-2/CHANGES.html file)

## 3. Installing (Ubuntu 14.04)

#### step 1

`sudo apt-get install tcl8.5-dev tk8.5-dev`

`sudo apt-get install build-essential autoconf automake`

`sudo apt-get install perl xgraph libxt-dev libx11-dev libxmu-dev`

#### step 2

`cd /path-to/ns-allinone-2.35`

`./install`

#### After set environment variables (.bashrc example)

`export PATH=$PATH:/path-tons-allinone-2.35/bin:/path-to/ns-allinone-2.35/tcl8.5.10/unix:/path-to/ns-allinone-2.35/tk8.5.10/unix`

`export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/path-to/ns-allinone-2.35/otcl-1.14:/path-to/ns-allinone-2.35/lib`

`export TCL_LIBRARY=$TCL_LIBRARY:/path-to/ns-allinone-2.35/tcl8.5.10/library`

#### Ready!

## 4. More information

> Ns-allinone is available from
<http://sourceforge.net/projects/nsnam>
or
<http://www.isi.edu/nsnam/ns/ns-build.html>


> The nsnam Project
http://www.nsnam.org

