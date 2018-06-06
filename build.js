mkdir("build");
mkdir("build/classes");

javac("src", "build/classes");

mkdir("dist");
jar("dist/infobus.jar", "build/classes", ".*", "manifest.mf");

publish("dist")
