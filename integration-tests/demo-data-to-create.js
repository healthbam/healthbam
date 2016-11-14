// need to save the orgIds for creating programs later

var Organization = angular.element(document.body).injector().get('Organization'),
    Program = angular.element(document.body).injector().get('Program'),
    $q = angular.element(document.body).injector().get('$q'),
    programsToCreate,
    childrensHospitalOfGeorgia = new Organization({
        name: "Children's Hospital of Georgia",
        websiteUrl: "http://www.augustahealth.org/childrens-hospital-of-georgia"
    }),
    columbusRegionalHealth = new Organization({
        name: "Columbus Regional Health",
        websiteUrl: "https://columbusregional.com/"
    }),
    fultonCountyDeptOfHealth = new Organization({
        name: "Fulton County Department of Health and Wellness",
        websiteUrl: "http://www.fultoncountyga.gov/dhw-home"
    }),
    hamiltonMedicalCenter = new Organization({
        name: "Hamilton Medical Center",
        websiteUrl: "http://www.hamiltonhealth.com/NICU"
    }),
    memorialUniversity = new Organization({
        name: "Memorial University",
        websiteUrl: "https://www.memorialhealth.com"
    }),
    navicentHealth = new Organization({
        name: "Navicent Health Women's Services", 
        websiteUrl: "https://www.navicenthealth.org"
    }),
    orgsToCreate,
    orgPromises = [];

orgsToCreate = [
    childrensHospitalOfGeorgia,
    columbusRegionalHealth,
    fultonCountyDeptOfHealth,
    hamiltonMedicalCenter,
    memorialUniversity,
    navicentHealth
];

orgsToCreate.forEach(
    function (org) {
        orgPromises.push(org.$save());
    }
);


$q.all(orgPromises).then(
    function () {

        programsToCreate = [
            {
                "city": "Atlanta", 
                "coordinates": "-84.38207930,33.75167090", 
                "countiesServed": [
                    {
                        "id": 147, 
                        "name": "Walton", 
                        "state": "GA"
                    }, 
                    {
                        "id": 56, 
                        "name": "Fayette", 
                        "state": "GA"
                    }, 
                    {
                        "id": 122, 
                        "name": "Rockdale", 
                        "state": "GA"
                    }, 
                    {
                        "id": 31, 
                        "name": "Clayton", 
                        "state": "GA"
                    }, 
                    {
                        "id": 7, 
                        "name": "Barrow", 
                        "state": "GA"
                    }, 
                    {
                        "id": 67, 
                        "name": "Gwinnett", 
                        "state": "GA"
                    }, 
                    {
                        "id": 108, 
                        "name": "Oconee", 
                        "state": "GA"
                    }, 
                    {
                        "id": 75, 
                        "name": "Henry", 
                        "state": "GA"
                    }, 
                    {
                        "id": 78, 
                        "name": "Jackson", 
                        "state": "GA"
                    }, 
                    {
                        "id": 110, 
                        "name": "Paulding", 
                        "state": "GA"
                    }, 
                    {
                        "id": 48, 
                        "name": "Douglas", 
                        "state": "GA"
                    }, 
                    {
                        "id": 43, 
                        "name": "DeKalb", 
                        "state": "GA"
                    }, 
                    {
                        "id": 60, 
                        "name": "Fulton", 
                        "state": "GA"
                    }, 
                    {
                        "id": 29, 
                        "name": "Clarke", 
                        "state": "GA"
                    }
                ], 
                "measurableOutcome1": "Improved outcomes...", 
                "name": "Grady Hospital NICU", 
                "organization": {
                    "id": fultonCountyDeptOfHealth.id
                }, 
                "primaryGoal1": "Premature and high-risk babies are cared for by a dedicated staff of neonatologists and neonatal nurses in our NICU. ", 
                "programAreas": [
                    {
                        "id": 12, 
                        "name": "Neonatal Care"
                    }
                ], 
                "servesAllCounties": false, 
                "state": "GA", 
                "streetAddress": "80 Jesse Hill Jr Drive SE", 
                "zipCode": "30303"
            }, 
            {
                "city": "Savannah", 
                "coordinates": "-81.08948450,32.02998600", 
                "countiesServed": [
                    {
                        "id": 16, 
                        "name": "Bulloch", 
                        "state": "GA"
                    }, 
                    {
                        "id": 50, 
                        "name": "Echols", 
                        "state": "GA"
                    }, 
                    {
                        "id": 113, 
                        "name": "Pierce", 
                        "state": "GA"
                    }, 
                    {
                        "id": 21, 
                        "name": "Candler", 
                        "state": "GA"
                    }, 
                    {
                        "id": 25, 
                        "name": "Chatham", 
                        "state": "GA"
                    }, 
                    {
                        "id": 148, 
                        "name": "Ware", 
                        "state": "GA"
                    }, 
                    {
                        "id": 51, 
                        "name": "Effingham", 
                        "state": "GA"
                    }, 
                    {
                        "id": 63, 
                        "name": "Glynn", 
                        "state": "GA"
                    }, 
                    {
                        "id": 2, 
                        "name": "Atkinson", 
                        "state": "GA"
                    }, 
                    {
                        "id": 34, 
                        "name": "Coffee", 
                        "state": "GA"
                    }, 
                    {
                        "id": 54, 
                        "name": "Evans", 
                        "state": "GA"
                    }, 
                    {
                        "id": 132, 
                        "name": "Tattnall", 
                        "state": "GA"
                    }, 
                    {
                        "id": 151, 
                        "name": "Wayne", 
                        "state": "GA"
                    }, 
                    {
                        "id": 86, 
                        "name": "Lanier", 
                        "state": "GA"
                    }, 
                    {
                        "id": 91, 
                        "name": "Long", 
                        "state": "GA"
                    }, 
                    {
                        "id": 15, 
                        "name": "Bryan", 
                        "state": "GA"
                    }, 
                    {
                        "id": 80, 
                        "name": "Jeff Davis", 
                        "state": "GA"
                    }, 
                    {
                        "id": 1, 
                        "name": "Appling", 
                        "state": "GA"
                    }, 
                    {
                        "id": 32, 
                        "name": "Clinch", 
                        "state": "GA"
                    }, 
                    {
                        "id": 3, 
                        "name": "Bacon", 
                        "state": "GA"
                    }, 
                    {
                        "id": 20, 
                        "name": "Camden", 
                        "state": "GA"
                    }, 
                    {
                        "id": 89, 
                        "name": "Liberty", 
                        "state": "GA"
                    }, 
                    {
                        "id": 24, 
                        "name": "Charlton", 
                        "state": "GA"
                    }, 
                    {
                        "id": 95, 
                        "name": "McIntosh", 
                        "state": "GA"
                    }, 
                    {
                        "id": 138, 
                        "name": "Toombs", 
                        "state": "GA"
                    }, 
                    {
                        "id": 13, 
                        "name": "Brantley", 
                        "state": "GA"
                    }
                ], 
                "createdBy": "jslide07@gmail.com", 
                "createdOn": 1478044800000, 
                "measurableOutcome1": "Improve outcomes...", 
                "measurableOutcome2": null, 
                "measurableOutcome3": null, 
                "name": "Memorial University Medical Center", 
                "organization": {
                    "id": memorialUniversity.id
                }, 
                "otherProgramAreaExplanation": null, 
                "primaryGoal1": "Premature and high-risk babies are cared for by a dedicated staff of neonatologists and neonatal nurses in our NICU. ", 
                "primaryGoal2": null, 
                "primaryGoal3": null, 
                "programAreas": [
                    {
                        "id": 12, 
                        "name": "Neonatal Care"
                    }
                ], 
                "servesAllCounties": false, 
                "startYear": null, 
                "state": "GA", 
                "streetAddress": "4700 Waters Avenue", 
                "updatedBy": "jslide07@gmail.com", 
                "updatedOn": 1478044800000, 
                "zipCode": "31404"
            }, 
            {
                "city": "Augusta", 
                "coordinates": "-81.98789290,33.47119740", 
                "countiesServed": [
                    {
                        "id": 62, 
                        "name": "Glascock", 
                        "state": "GA"
                    }, 
                    {
                        "id": 157, 
                        "name": "Wilkes", 
                        "state": "GA"
                    }, 
                    {
                        "id": 82, 
                        "name": "Jenkins", 
                        "state": "GA"
                    }, 
                    {
                        "id": 94, 
                        "name": "McDuffie", 
                        "state": "GA"
                    }, 
                    {
                        "id": 81, 
                        "name": "Jefferson", 
                        "state": "GA"
                    }, 
                    {
                        "id": 124, 
                        "name": "Screven", 
                        "state": "GA"
                    }, 
                    {
                        "id": 97, 
                        "name": "Madison", 
                        "state": "GA"
                    }, 
                    {
                        "id": 66, 
                        "name": "Greene", 
                        "state": "GA"
                    }, 
                    {
                        "id": 149, 
                        "name": "Warren", 
                        "state": "GA"
                    }, 
                    {
                        "id": 121, 
                        "name": "Richmond", 
                        "state": "GA"
                    }, 
                    {
                        "id": 131, 
                        "name": "Taliaferro", 
                        "state": "GA"
                    }, 
                    {
                        "id": 52, 
                        "name": "Elbert", 
                        "state": "GA"
                    }, 
                    {
                        "id": 70, 
                        "name": "Hancock", 
                        "state": "GA"
                    }, 
                    {
                        "id": 150, 
                        "name": "Washington", 
                        "state": "GA"
                    }, 
                    {
                        "id": 36, 
                        "name": "Columbia", 
                        "state": "GA"
                    }, 
                    {
                        "id": 90, 
                        "name": "Lincoln", 
                        "state": "GA"
                    }, 
                    {
                        "id": 17, 
                        "name": "Burke", 
                        "state": "GA"
                    }
                ], 
                "createdBy": "jslide07@gmail.com", 
                "createdOn": 1478044800000, 
                "measurableOutcome1": "Improved outcomes...", 
                "measurableOutcome2": null, 
                "measurableOutcome3": null, 
                "name": "Neonatal ICU", 
                "organization": {
                    "id": childrensHospitalOfGeorgia.id
                }, 
                "otherProgramAreaExplanation": null, 
                "primaryGoal1": "Premature and high-risk babies are cared for by a dedicated staff of neonatologists and neonatal nurses in our NICU. ", 
                "primaryGoal2": null, 
                "primaryGoal3": null, 
                "programAreas": [
                    {
                        "id": 12, 
                        "name": "Neonatal Care"
                    }
                ], 
                "servesAllCounties": false, 
                "startYear": null, 
                "state": "GA", 
                "streetAddress": "1446 Harper Street", 
                "updatedBy": "jslide07@gmail.com", 
                "updatedOn": 1478044800000, 
                "zipCode": "30912"
            }, 
            {
                "city": "Columbus", 
                "coordinates": "-84.98166650,32.48052200", 
                "countiesServed": [
                    {
                        "id": 159, 
                        "name": "Worth", 
                        "state": "GA"
                    }, 
                    {
                        "id": 46, 
                        "name": "Dooly", 
                        "state": "GA"
                    }, 
                    {
                        "id": 120, 
                        "name": "Randolph", 
                        "state": "GA"
                    }, 
                    {
                        "id": 72, 
                        "name": "Harris", 
                        "state": "GA"
                    }, 
                    {
                        "id": 88, 
                        "name": "Lee", 
                        "state": "GA"
                    }, 
                    {
                        "id": 137, 
                        "name": "Tift", 
                        "state": "GA"
                    }, 
                    {
                        "id": 9, 
                        "name": "Ben Hill", 
                        "state": "GA"
                    }, 
                    {
                        "id": 141, 
                        "name": "Troup", 
                        "state": "GA"
                    }, 
                    {
                        "id": 77, 
                        "name": "Irwin", 
                        "state": "GA"
                    }, 
                    {
                        "id": 40, 
                        "name": "Crisp", 
                        "state": "GA"
                    }, 
                    {
                        "id": 92, 
                        "name": "Lowndes", 
                        "state": "GA"
                    }, 
                    {
                        "id": 130, 
                        "name": "Talbot", 
                        "state": "GA"
                    }, 
                    {
                        "id": 96, 
                        "name": "Macon", 
                        "state": "GA"
                    }, 
                    {
                        "id": 106, 
                        "name": "Muscogee", 
                        "state": "GA"
                    }, 
                    {
                        "id": 136, 
                        "name": "Thomas", 
                        "state": "GA"
                    }, 
                    {
                        "id": 10, 
                        "name": "Berrien", 
                        "state": "GA"
                    }, 
                    {
                        "id": 65, 
                        "name": "Grady", 
                        "state": "GA"
                    }, 
                    {
                        "id": 142, 
                        "name": "Turner", 
                        "state": "GA"
                    }, 
                    {
                        "id": 37, 
                        "name": "Cook", 
                        "state": "GA"
                    }, 
                    {
                        "id": 47, 
                        "name": "Dougherty", 
                        "state": "GA"
                    }, 
                    {
                        "id": 152, 
                        "name": "Webster", 
                        "state": "GA"
                    }, 
                    {
                        "id": 49, 
                        "name": "Early", 
                        "state": "GA"
                    }, 
                    {
                        "id": 35, 
                        "name": "Colquitt", 
                        "state": "GA"
                    }, 
                    {
                        "id": 129, 
                        "name": "Sumter", 
                        "state": "GA"
                    }, 
                    {
                        "id": 133, 
                        "name": "Taylor", 
                        "state": "GA"
                    }, 
                    {
                        "id": 135, 
                        "name": "Terrell", 
                        "state": "GA"
                    }, 
                    {
                        "id": 19, 
                        "name": "Calhoun", 
                        "state": "GA"
                    }, 
                    {
                        "id": 14, 
                        "name": "Brooks", 
                        "state": "GA"
                    }, 
                    {
                        "id": 118, 
                        "name": "Quitman", 
                        "state": "GA"
                    }, 
                    {
                        "id": 30, 
                        "name": "Clay", 
                        "state": "GA"
                    }, 
                    {
                        "id": 44, 
                        "name": "Decatur", 
                        "state": "GA"
                    }, 
                    {
                        "id": 123, 
                        "name": "Schley", 
                        "state": "GA"
                    }, 
                    {
                        "id": 26, 
                        "name": "Chattahoochee", 
                        "state": "GA"
                    }, 
                    {
                        "id": 98, 
                        "name": "Marion", 
                        "state": "GA"
                    }, 
                    {
                        "id": 100, 
                        "name": "Miller", 
                        "state": "GA"
                    }, 
                    {
                        "id": 101, 
                        "name": "Mitchell", 
                        "state": "GA"
                    }, 
                    {
                        "id": 125, 
                        "name": "Seminole", 
                        "state": "GA"
                    }, 
                    {
                        "id": 128, 
                        "name": "Stewart", 
                        "state": "GA"
                    }, 
                    {
                        "id": 4, 
                        "name": "Baker", 
                        "state": "GA"
                    }
                ], 
                "measurableOutcome1": "Improved outcomes...", 
                "name": "NICU, Children's Hospital at Midtown", 
                "organization": {
                    "id": columbusRegionalHealth.id
                }, 
                "primaryGoal1": "Premature and high-risk babies are cared for by a dedicated staff of neonatologists and neonatal nurses in our NICU. ", 
                "programAreas": [
                    {
                        "id": 12, 
                        "name": "Neonatal Care"
                    }
                ], 
                "servesAllCounties": false, 
                "startYear": null, 
                "state": "GA", 
                "streetAddress": "710 Center St", 
                "zipCode": "31901"
            }, 
            {
                "city": "Macon", 
                "coordinates": "-83.63531930,32.83388060", 
                "countiesServed": [
                    {
                        "id": 12, 
                        "name": "Bleckley", 
                        "state": "GA"
                    }, 
                    {
                        "id": 83, 
                        "name": "Johnson", 
                        "state": "GA"
                    }, 
                    {
                        "id": 76, 
                        "name": "Houston", 
                        "state": "GA"
                    }, 
                    {
                        "id": 153, 
                        "name": "Wheeler", 
                        "state": "GA"
                    }, 
                    {
                        "id": 11, 
                        "name": "Bibb", 
                        "state": "GA"
                    }, 
                    {
                        "id": 85, 
                        "name": "Lamar", 
                        "state": "GA"
                    }, 
                    {
                        "id": 22, 
                        "name": "Carroll", 
                        "state": "GA"
                    }, 
                    {
                        "id": 156, 
                        "name": "Wilcox", 
                        "state": "GA"
                    }, 
                    {
                        "id": 111, 
                        "name": "Peach", 
                        "state": "GA"
                    }, 
                    {
                        "id": 79, 
                        "name": "Jasper", 
                        "state": "GA"
                    }, 
                    {
                        "id": 38, 
                        "name": "Coweta", 
                        "state": "GA"
                    }, 
                    {
                        "id": 99, 
                        "name": "Meriwether", 
                        "state": "GA"
                    }, 
                    {
                        "id": 5, 
                        "name": "Baldwin", 
                        "state": "GA"
                    }, 
                    {
                        "id": 84, 
                        "name": "Jones", 
                        "state": "GA"
                    }, 
                    {
                        "id": 145, 
                        "name": "Upson", 
                        "state": "GA"
                    }, 
                    {
                        "id": 87, 
                        "name": "Laurens", 
                        "state": "GA"
                    }, 
                    {
                        "id": 107, 
                        "name": "Newton", 
                        "state": "GA"
                    }, 
                    {
                        "id": 116, 
                        "name": "Pulaski", 
                        "state": "GA"
                    }, 
                    {
                        "id": 45, 
                        "name": "Dodge", 
                        "state": "GA"
                    }, 
                    {
                        "id": 103, 
                        "name": "Montgomery", 
                        "state": "GA"
                    }, 
                    {
                        "id": 117, 
                        "name": "Putnam", 
                        "state": "GA"
                    }, 
                    {
                        "id": 114, 
                        "name": "Pike", 
                        "state": "GA"
                    }, 
                    {
                        "id": 143, 
                        "name": "Twiggs", 
                        "state": "GA"
                    }, 
                    {
                        "id": 102, 
                        "name": "Monroe", 
                        "state": "GA"
                    }, 
                    {
                        "id": 140, 
                        "name": "Treutlen", 
                        "state": "GA"
                    }, 
                    {
                        "id": 18, 
                        "name": "Butts", 
                        "state": "GA"
                    }, 
                    {
                        "id": 53, 
                        "name": "Emanuel", 
                        "state": "GA"
                    }, 
                    {
                        "id": 158, 
                        "name": "Wilkinson", 
                        "state": "GA"
                    }, 
                    {
                        "id": 39, 
                        "name": "Crawford", 
                        "state": "GA"
                    }, 
                    {
                        "id": 104, 
                        "name": "Morgan", 
                        "state": "GA"
                    }, 
                    {
                        "id": 74, 
                        "name": "Heard", 
                        "state": "GA"
                    }, 
                    {
                        "id": 134, 
                        "name": "Telfair", 
                        "state": "GA"
                    }, 
                    {
                        "id": 126, 
                        "name": "Spalding", 
                        "state": "GA"
                    }
                ], 
                "createdBy": "jslide07@gmail.com", 
                "createdOn": 1478044800000, 
                "measurableOutcome1": "Improved outcomes...", 
                "measurableOutcome2": null, 
                "measurableOutcome3": null, 
                "name": "NNICU, Children's Hospital at Medical Center of Central Georgia", 
                "organization": {
                    "id": navicentHealth.id 
                }, 
                "otherProgramAreaExplanation": null, 
                "primaryGoal1": "Premature and high-risk babies are cared for by a dedicated staff of neonatologists and neonatal nurses in our NICU. ", 
                "primaryGoal2": null, 
                "primaryGoal3": null, 
                "programAreas": [
                    {
                        "id": 12, 
                        "name": "Neonatal Care"
                    }
                ], 
                "servesAllCounties": false, 
                "startYear": null, 
                "state": "GA", 
                "streetAddress": "777 Hemlock Street", 
                "updatedBy": "jslide07@gmail.com", 
                "updatedOn": 1478044800000, 
                "zipCode": "31201"
            }, 
            {
                "city": "Dalton", 
                "coordinates": "-84.98592800,34.79048910", 
                "countiesServed": [
                    {
                        "id": 105, 
                        "name": "Murray", 
                        "state": "GA"
                    }, 
                    {
                        "id": 73, 
                        "name": "Hart", 
                        "state": "GA"
                    }, 
                    {
                        "id": 146, 
                        "name": "Walker", 
                        "state": "GA"
                    }, 
                    {
                        "id": 41, 
                        "name": "Dade", 
                        "state": "GA"
                    }, 
                    {
                        "id": 55, 
                        "name": "Fannin", 
                        "state": "GA"
                    }, 
                    {
                        "id": 155, 
                        "name": "Whitfield", 
                        "state": "GA"
                    }, 
                    {
                        "id": 154, 
                        "name": "White", 
                        "state": "GA"
                    }, 
                    {
                        "id": 23, 
                        "name": "Catoosa", 
                        "state": "GA"
                    }, 
                    {
                        "id": 119, 
                        "name": "Rabun", 
                        "state": "GA"
                    }, 
                    {
                        "id": 144, 
                        "name": "Union", 
                        "state": "GA"
                    }, 
                    {
                        "id": 42, 
                        "name": "Dawson", 
                        "state": "GA"
                    }, 
                    {
                        "id": 58, 
                        "name": "Forsyth", 
                        "state": "GA"
                    }, 
                    {
                        "id": 112, 
                        "name": "Pickens", 
                        "state": "GA"
                    }, 
                    {
                        "id": 139, 
                        "name": "Towns", 
                        "state": "GA"
                    }, 
                    {
                        "id": 127, 
                        "name": "Stephens", 
                        "state": "GA"
                    }, 
                    {
                        "id": 27, 
                        "name": "Chattooga", 
                        "state": "GA"
                    }, 
                    {
                        "id": 93, 
                        "name": "Lumpkin", 
                        "state": "GA"
                    }, 
                    {
                        "id": 115, 
                        "name": "Polk", 
                        "state": "GA"
                    }, 
                    {
                        "id": 59, 
                        "name": "Franklin", 
                        "state": "GA"
                    }, 
                    {
                        "id": 71, 
                        "name": "Haralson", 
                        "state": "GA"
                    }, 
                    {
                        "id": 69, 
                        "name": "Hall", 
                        "state": "GA"
                    }, 
                    {
                        "id": 6, 
                        "name": "Banks", 
                        "state": "GA"
                    }, 
                    {
                        "id": 64, 
                        "name": "Gordon", 
                        "state": "GA"
                    }, 
                    {
                        "id": 68, 
                        "name": "Habersham", 
                        "state": "GA"
                    }, 
                    {
                        "id": 61, 
                        "name": "Gilmer", 
                        "state": "GA"
                    }, 
                    {
                        "id": 57, 
                        "name": "Floyd", 
                        "state": "GA"
                    }
                ], 
                "measurableOutcome1": "Improved outcomes...", 
                "name": "Turner Neonatal Intensive Care Pavilion", 
                "organization": {
                    "id": hamiltonMedicalCenter.id
                }, 
                "primaryGoal1": "Premature and high-risk babies are cared for by a dedicated staff of neonatologists and neonatal nurses in our NICU. ", 
                "programAreas": [
                    {
                        "id": 12, 
                        "name": "Neonatal Care"
                    }
                ], 
                "servesAllCounties": false, 
                "state": "GA", 
                "streetAddress": "1101 Burleyson Road", 
                "zipCode": "30720"
            }
        ];

        programsToCreate.forEach(
            function (program) {
                new Program(program).$save();
            }
        );
    }
);
