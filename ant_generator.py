#!/usr/bin/python3

from argparse import ArgumentParser


lines = [
    '<?xml version="1.0" encoding="UTF-8" standalone="no"?>',
    '<project default="create_run_jar" name="Create Runnable Jar for Project vacuumworld-2.0">',
    "\t" + '<property name="dir.buildfile" value="."/>',
    "\t" + '<property name="dir.workspace" value="${dir.buildfile}/.."/>',
    "\t" + '<property name="dir.jarfile" value="~"/>',
    "\t" + '<target name="create_run_jar">',
    "\t\t" + '<jar destfile="${dir.jarfile}/~" filesetmanifest="mergewithoutmain">',
    "\t\t\t" + '<manifest>',
    "\t\t\t\t" + '<attribute name="Main-Class" value="uk.ac.rhul.cs.dice.vacuumworld.VacuumWorld"/>',
    "\t\t\t\t" + '<attribute name="Class-Path" value="."/>',
    "\t\t\t" + '</manifest>',
    "\t\t\t" + '<fileset dir="${dir.buildfile}/target/classes"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/junit/jupiter/junit-jupiter-api/5.0.0/junit-jupiter-api-5.0.0.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/apiguardian/apiguardian-api/1.0.0/apiguardian-api-1.0.0.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/opentest4j/opentest4j/1.0.0/opentest4j-1.0.0.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/junit/platform/junit-platform-commons/1.0.0/junit-platform-commons-1.0.0.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/junit/platform/junit-platform-engine/1.0.0-M5/junit-platform-engine-1.0.0-M5.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/junit/vintage/junit-vintage-engine/4.12.0/junit-vintage-engine-4.12.0.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/junit/junit/4.12/junit-4.12.jar"/>',
    "\t\t\t" + '<zipfileset excludes="META-INF/*.SF" src="${dir.jarfile}/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar"/>',
    "\t\t\t" + '<fileset dir="${dir.workspace}/starworlds-lite/target/classes"/>',
    "\t\t" + '</jar>',
    "\t" + '</target>',
    '</project>'
]


def __parse_args():
    parser = ArgumentParser(description="Nmap-output-to-json parser")
    parser.add_argument('-H', '--home', required=True, metavar='<home-path>', type=str, action='store',
                        help='Home path')
    parser.add_argument('-j', '--jar', required=True, metavar='<jar-name>', type=str, action='store',
                        help='JAR name')

    args = parser.parse_args()

    return args.home, args.jar


def build_ant_script():
    home_path, jar_name = __parse_args()
    tokens = lines[4].split("~")
    lines[4] = tokens[0] + home_path + tokens[1]
    tokens = lines[6].split("~")
    lines[6] = tokens[0] + jar_name + tokens[1]
    
    with open("ant.xml", "w") as dst:
        for line in lines:
            dst.write(line + "\n")


if __name__ == "__main__":
    print("Generating ANT script...")
    build_ant_script()
    print("Done.")
