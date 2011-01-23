/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
/**
 * This javascript file defines classes to store informations about contest.
 * 
 * <p>
 * Changes in v1.0.1 (TC Direct - Launch Copilot Selection Contest assembly): add private description field in project class,
 * and copilot selection contest in project category array.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0.1
 */
if(!com) {
   var com = {};
}

if(!com.topcoder) {
   com.topcoder = {};
}

if(!com.topcoder.direct) {
   com.topcoder.direct = {};
}

/**
 * Constants for studio status
 */
var CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED = 1;
var CONTEST_STATUS_ACTIVE_PUBLIC = 2 ;
var CONTEST_DETAILED_STATUS_DRAFT =15 ;
var CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC =2 ;
var CONTEST_DETAILED_STATUS_SCHEDULED =9 ;


/**
 * Studio Competition Classes
 */
com.topcoder.direct.StudioCompetition = function() {
   this.id = -1;

   this.type = null;

   this.contestData = new com.topcoder.direct.ContestData();

   //Dates
   this.startDate = null;

   this.endDate	= null;

   this.milestoneDate = null;
}

/**
 * Contest data class.
 */
com.topcoder.direct.ContestData = function() {
   this.contestId = -1;
   
   this.contestTypeId = -1;

   this.name = null;

   this.projectId = -1;

   this.tcDirectProjectId = -1;

   this.tcDirectProjectName = null;

   this.billingProject = -1;

   this.multiRound = false;
   
   this.shortSummary = null;

   this.contestDescriptionAndRequirements = null;

   this.prizes = [];

   this.multiRoundData = 	new com.topcoder.direct.ContestMultiRoundInformationData();

   this.finalFileFormat = '';

   this.otherFileFormats = '';

   this.statusId=CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED;

   this.detailedStatusId=CONTEST_DETAILED_STATUS_DRAFT;

   
   ////////// Functions/Methods ///////////////
   /**
    * If the billing project is selected.
    *
    * @return true if the billing project is selected. otherwise false.
    */
   this.isBillingSelected = function() {
         return (this.billingProject > 0);
   }
}

/**
 * Contest multiple round information data.
 */
com.topcoder.direct.ContestMultiRoundInformationData = function() {
   this.id = -1;

   //this.milestoneDate = null;
   
   this.roundOneIntroduction = null;
   
   this.roundTwoIntroduction = null;
}

/**
 * Milestone prize data class.
 */
com.topcoder.direct.MilestonePrizeData = function() {
   this.id = -1;

   this.amount = 0;

   this.numberOfSubmissions = 0;
}

/**
 * Prize data class.
 * 
 * @param place the place
 * @param amount the amount
 */
com.topcoder.direct.PrizeData = function(place, amount) {
   this.place = place;

   this.amount = amount;
}

/**
 * Software Constants
 */
var REQUIREMENTS_SPECIFICATION_DOCUMENT_TYPE_ID = 0;
var SUPPORTING_DOCUMENTATION_DOCUMENT_TYPE_ID = 24;
 
//cost level low
var COST_LEVEL_LOW = "A";
var COST_LEVEL_MEDIUM = "B";
var COST_LEVEL_HIGH = "C";
var COST_LEVEL_CUSTOM= "M";
var RADIOVALUE_COSTLEVEL_MAP = {"low":COST_LEVEL_LOW, "medium":COST_LEVEL_MEDIUM, "high":COST_LEVEL_HIGH, "custom":COST_LEVEL_CUSTOM};
var COSTLEVEL_RADIOVALUE_MAP = {};
for(var radioValue in RADIOVALUE_COSTLEVEL_MAP) {
	COSTLEVEL_RADIOVALUE_MAP[RADIOVALUE_COSTLEVEL_MAP[radioValue]] = radioValue;
}
 
var SOFTWARE_CATEGORY_ID_DESIGN = 1;
var SOFTWARE_CATEGORY_ID_DEVELOPMENT = 2;
var SOFTWARE_CATEGORY_ID_CONCEPT = 23;
var SOFTWARE_CATEGORY_ID_SPEC = 6;
var SOFTWARE_CATEGORY_ID_CONTENT = 35;
 
var projectCategoryArray = [
{id:SOFTWARE_CATEGORY_ID_CONCEPT,      name:'CONCEPTUALIZATION', label:'Software Conceptualization', typeId:2, typeName:'Application'},
{id:SOFTWARE_CATEGORY_ID_SPEC,         name:'SPECIFICATION',     label:'Software Specification',     typeId:2, typeName:'Application'},
{id:7,                                 name:'ARCHITECTURE',      label:'Architecture',               typeId:2, typeName:'Application'},
{id:SOFTWARE_CATEGORY_ID_DESIGN,       name:'DESIGN',            label:'Component Design',           typeId:1, typeName:'Component'},
{id:SOFTWARE_CATEGORY_ID_DEVELOPMENT,  name:'DEVELOPMENT',       label:'Component Development',      typeId:1, typeName:'Component'},
{id:25,                                name:'RIACOMPONENT',      label:'RIA Component',              typeId:2, typeName:'Application'},
{id:24,                                name:'RIABUILD',          label:'RIA Build',                  typeId:2, typeName:'Application'},
{id:19,                                name:'UIPROTOTYPE',       label:'UI Prototype',               typeId:2, typeName:'Application'},
{id:14,                                name:'ASSEMBLY',          label:'Software Assembly',          typeId:2, typeName:'Application'},
{id:13,                                name:'TESTSUITES',        label:'Test Suites',                typeId:2, typeName:'Application'},
{id:26,                                name:'TESTSCENARIOS',     label:'Test Scenarios',             typeId:2, typeName:'Application'},
{id:29,                                name:'Copilot Posting',   label:'Copilot Posting',            typeId:2, typeName:'Application'},
{id:SOFTWARE_CATEGORY_ID_CONTENT,      name:'Content Creation',  label:'Content Creation',           typeId:2, typeName:'Application'}
];

/**
 * Get project category by id.
 * 
 * @param id the id
 * @return project category
 */
function getProjectCategoryById(id) {
     return $.grep(projectCategoryArray,function(element, i) {
           return element.id == id;
     }) [0];
}

/**
 * Get project category id by name.
 * 
 * @param name the name.
 * @return project category id, or -1 if not found.
 */
function getProjectCategoryIdByName(name) {
	 var category = $.grep(projectCategoryArray,function(element, i) {
	 	  return element.name == name;
	 }) [0];
	 if(category) {
	 	  return category.id;
	 } else {
	 	  return -1;
	 } 
}

/**
 * Software Competition Classes
 */
com.topcoder.direct.SoftwareCompetition = function() {	  
	  this.assetDTO = new com.topcoder.direct.AssetDTO();
	
    this.projectHeader = new com.topcoder.direct.Project();    
    
    // holding the end date information
    this.endDate = null;
    
    // holding the paid fee
    this.paidFee = 0;
} 

/**
 * Asset DTO classes.
 */
com.topcoder.direct.AssetDTO = function() {
	  this.name = null;	  

    this.directjsTechnologies = [];
    this.directjsRootCategoryId = -1;
    this.directjsCategories = [];
	  
	  //hold date ojbect
	  this.directjsProductionDate = null;
	  //hold date string for sending
	  this.productionDate = null;
} 

/**
 * Project class.
 */
com.topcoder.direct.Project = function() {
	  this.id = -1;
	  
	  this.tcDirectProjectId = -1;	
	  
	  this.projectSpec = new com.topcoder.direct.ProjectSpec();
	  
	  this.properties = {};
	  
	  this.setBillingProject = function(billingProjectId) {
	  	  this.properties['Billing Project'] = billingProjectId;
	  }
	  
	  this.getBillingProject = function() {
	  	  return this.properties['Billing Project'];
	  }

	  this.setProjectName = function(projectName) {
	  	  this.properties['Project Name'] = projectName;
	  }
	  
	  this.getProjectName = function() {
	  	  return this.properties['Project Name'];
	  }	  
	  
	  this.setRootCatalogId = function(rootCatalogId) {
	  	  this.properties['Root Catalog ID'] = rootCatalogId;
	  }

	  this.setConfidentialityTypePublic = function() {
	  	  this.properties['Confidentiality Type'] = 'public';
	  }
	  
	  this.setConfidentialityTypePrivate = function() {
	  	  this.properties['Confidentiality Type'] = 'standard_cca';
	  }
	  
	  this.isLccchecked = function() {
	  	 return "standard_cca" == this.properties['Confidentiality Type'];
	  }
	  
	  //fees
	  this.setFirstPlaceCost = function(firstPlaceCost) {
	  	  this.properties['First Place Cost'] = firstPlaceCost;
	  	  // per cockpit, it might be changed to total fee later
	  	  this.properties['Payments'] = firstPlaceCost;	  	  
	  }
	  
	  this.getFirstPlaceCost = function() {
	  	  //return this.properties['First Place Cost'];
	  	  // see http://forums.topcoder.com/?module=Thread&threadID=678314&start=0 
	  	  // OR will update this property
	  	  return this.properties['Payments'];
	  }

	  this.setSecondPlaceCost = function(secondPlaceCost) {
	  	  this.properties['Second Place Cost'] = secondPlaceCost;
	  }
	  
	  this.getSecondPlaceCost = function() {
	  	  return this.properties['Second Place Cost'];
	  }

	  this.setReviewCost = function(reviewCost) {
	  	  this.properties['Review Cost'] = reviewCost;
	  }

	  this.getReviewCost = function() {
	  	  return this.properties['Review Cost'];
	  }

	  this.setReliabilityBonusCost = function(reliabilityBonusCost) {
	  	  this.properties['Reliability Bonus Cost'] = reliabilityBonusCost;
	  }

	  this.getReliabilityBonusCost = function() {
	  	  return this.properties['Reliability Bonus Cost'];
	  }

	  this.setDRPoints = function(drPoints) {
	  	  this.properties['DR points'] = drPoints;
	  }

	  this.getDRPoints = function() {
	  	  return this.properties['DR points'];
	  }

	  this.setMilestoneBonusCost = function(milestoneBonusCost) {
	  	  this.properties['Milestone Bonus Cost'] = milestoneBonusCost;
	  }

	  this.getMilestoneBonusCost = function() {
	  	  return this.properties['Milestone Bonus Cost'];
	  }

	  this.setAdminFee = function(adminFee) {
	  	  this.properties['Admin Fee'] = adminFee;
	  }
	  
	  this.getAdminFee = function() {
	  	  return this.properties['Admin Fee'];
	  }

	  this.setSpecReviewCost = function(specReviewCost) {
	  	  this.properties['Spec Review Cost'] = specReviewCost;
	  }

	  this.getSpecReviewCost = function() {
	  	  return this.properties['Spec Review Cost'];
	  }

	  this.setCostLevel = function(costLevel) {
	  	 // 'A', 'B', 'C', 'M'
	  	  this.properties['Cost Level'] = costLevel;
	  }

	  this.getCostLevel = function() {
	  	  return this.properties['Cost Level'];
	  }

	  this.setBillingProject(0);
}

/**
 * Project spec class.
 */
com.topcoder.direct.ProjectSpec = function() {
      this.projectSpecId = 0;
      
      this.detailedRequirements = "";
      
      this.submissionDeliverables = "";
      
      this.environmentSetupInstructions = "";
      
      this.finalSubmissionGuidelines = "";
      
      /**
       * Represents private description field.
       * 
       * @since 1.0.1
       */
      this.privateDescription = "";
}


/**
 * Main Widget
 */
com.topcoder.direct.MainWidget = function() {
  //'STUDIO' or 'SOFTWARE'
  this.competitionType = null;

  // studio competition
  this.competition = new com.topcoder.direct.StudioCompetition();

  // holding the mileston prize information
  this.milestonePrizeData = new com.topcoder.direct.MilestonePrizeData();
  
  // software competition
  this.softwareCompetition = new com.topcoder.direct.SoftwareCompetition();
  
  this.isStudioContest = function() {
  	 return 'STUDIO' == this.competitionType;
  }
  
  this.isSoftwareContest =  function() {
  	 return 'SOFTWARE' == this.competitionType;
  }
}

