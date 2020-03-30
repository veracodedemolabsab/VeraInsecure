#Allows running a Veracode Code agent SCA scan to find differences between master and chosen branch in a Maven project
#Update guidance will be provided for each new libary a developer adds that creates an issue
#example useage:
#command line: srcclr scan --allow-dirty --update-advisor --json > scaresults && python3 sca_agent_comp_maven.py <branch>

import os
import sys
import re
import json

stream = os.popen('git diff master...%s pom.xml' % (sys.argv[1]))
pomDiff = stream.read()

if(len(pomDiff) == 0):
    print("No pom file changes")
    sys.exit()

parsedPomDiff = re.split(r'\n', pomDiff)

pomChanges = []

for line in parsedPomDiff:
    if(len(line) > 0):
        if(line[0] == "+" and "<groupId>" in line):
            cleanCoordinate1 = re.search('>(.+?)<', line)
            pomChanges.append(["Coordinate 1", cleanCoordinate1[1]])
        elif(line[0] == "+" and "<artifactId>" in line):
            cleanCoordinate2 = re.search('>(.+?)<', line)
            pomChanges.append(["Coordinate 2", cleanCoordinate2[1]])

if len(pomChanges) == 0:
    print("No added libraries")
    sys.exit()

newScaFlaws = []

with open('scaresults.json') as f:
  data = json.load(f)
updateAdvices = data["records"][0]["updateAdvisor"]["updateAdvices"]
for evidence in updateAdvices:
    if(["Coordinate 1", evidence["evidence"]["coordinates"]["coordinate1"]] in pomChanges and
       ["Coordinate 2", evidence["evidence"]["coordinates"]["coordinate2"]] in pomChanges):
        newScaFlaws.append([evidence["libraryName"], evidence["evidence"]["coordinates"]["version"], 
        evidence["updateToVersion"]])

if len(newScaFlaws) > 0:
    print("Ouch found %d new flaw in sca!" % (len(newScaFlaws)))
    for flaw in newScaFlaws:
        print("%s uses version %s and you should upgrade to %s" %(flaw[0], flaw[1], flaw[2]))
else:
    print("No new flawed libraries found!")

