#Allows running a Veracode Code agent SCA scan to find differences between new branch and master (via results file)
#Update guidance will be provided for each new libary a developer adds that creates an issue
#example useage:
#command line: srcclr scan --allow-dirty --update-advisor --json > new_sca_results.json && python3 sca_results_comparer.py

import json

baselineScaVulns = []
newScaVulns = []
deltaScaVulns = []

with open('sca_results.json') as baselineScaResultsFile:
    data = json.load(baselineScaResultsFile)
    updateAdvices = data["records"][0]["updateAdvisor"]["updateAdvices"]
    for evidence in updateAdvices:
        baselineScaVulns.append([evidence["libraryName"], evidence["evidence"]["coordinates"]["version"], 
        evidence["updateToVersion"]])
        
with open('new_sca_results.json') as newScaResultsFile:
    data = json.load(newScaResultsFile)
    updateAdvices = data["records"][0]["updateAdvisor"]["updateAdvices"]
    for evidence in updateAdvices:
        newScaVulns.append([evidence["libraryName"], evidence["evidence"]["coordinates"]["version"], 
        evidence["updateToVersion"]])

for scaVuln in newScaVulns:
    if scaVuln not in baselineScaVulns:
        deltaScaVulns.append(scaVuln)
        

if len(deltaScaVulns) > 0:
    print("Ouch found %d new flaw in sca!" % (len(deltaScaVulns)))
    for vuln in deltaScaVulns:
        print("%s uses version %s and you should upgrade to %s" %(vuln[0], vuln[1], vuln[2]))
else:
    print("No new flawed libraries found!")