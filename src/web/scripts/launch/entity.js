/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
/**
 * This javascript file defines classes to store informations about contest.
 * 
 * <p>
 * Changes in v1.0.1 (TC Direct - Launch Copilot Selection Contest assembly): add private description field in project class,
 * and copilot selection contest in project category array.
 * </p>
 * <p>
 * Changes in v1.0.2 (TC Direct - Software Creation Update assembly): 
 *  - Add copilotUserId and copilotUserName to SoftwareCompetition
 * </p>
 * <p>
 * Changes in 1.1 TC Direct Replatforming Release 1 change note
 * - Many changes were made to work for the new studio contest type and multiround type.
 * </p>
 * <p>
 * Changes in 1.2 TC Direct Replatforming Release 4 change note
 * - Change com.topcoder.direct.Projec to support art stocks and maximal submission limitation.
 * </p>
 * <p>
 * Changes in version 1.3 (TC Cockpit Post a Copilot Assembly 2):
 * <ul>
 * <li>Added ProjectCopilotType, CopilotContestExtraInfoType, and CopilotContestExtraInfo classes.</li>
 * <li>Added projectCopilotTypes and copilotContestExtraInfos fields to Project class.</li>
 * <li>Added two constant CopilotContestExtraInfoTypes.</li>
 * </ul>
 * Changes in version 1.4 (Release Assembly - TopCoder BugHunt Competition Integration):
 * <li>Add bug hunt to the contest types when creating new contest.</li>
 * </p>
 *
 * <p>
 *  Version 1.4.1 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) change notes:
 *  - Add constant SOFTWARE_CATEGORY_ID_ASSEMBLY
 * </p>
 *
 * <p>
 *  Version 1.5 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match) change notes:
 *  - Remove constant SOFTWARE_CATEGORY_ID_MARATHON
 *  - Add constant ALGORITHM_CATEGORY_ID_MARATHON
 *  - Add entity com.topcoder.direct.ProjectMMSpecification
 *  - Update entity com.topcoder.direct.Project to support mm specification
 *  - Update entity com.topcoder.direct.MainWidget to support ALGORITHM contest
 * </p>
 *
 * <p>
 *  Version 1.6 (Module Assembly - TC Cockpit Contest Milestone Association 1)
 *  - Add projectMilesoneId and projectMilestoneName to the SoftwareCompetition entity.
 * </p>
 *
 * <p>
 *   Version 1.7 (Module Assembly - TC Cockpit Launch F2F contest)
 *   - Add First2Finish contest type to software group
 * </p>
 * 
 *
 * <p>
 *   Version 1.8 (Module Assembly - TC Cockpit Launch Code contest)
 *   - Add Code contest type to software group
 * </p>
 *
 * Version 1.9 (Module Assembly - TC Direct Studio Design First2Finish Challenge Type)
 * - Add new Design First2Finish contest to studio group
 * 
 * @author duxiaoyang, bugbuka, GreatKevin
 * @version 1.9
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
var CHECKPOINT_PRIZE_TYPE_ID = 14;
var CONTEST_PRIZE_TYPE_ID = 15;



/**
 * Prize class.
 * 
 * @param place the place
 * @param amount the amount
 * @param prizeType the prize type id
 * @param number Of submissions the number of submissions 
 */
com.topcoder.direct.Prize = function(place, amount, prizeType, numberOfSubmissions) {
	this.place = place;
	
	this.prizeAmount = amount;
	
	this.prizeType = {};
	
	this.prizeType.id = prizeType;
	
	if (prizeType == CONTEST_PRIZE_TYPE_ID) {
		this.prizeType.description = "Contest Prize";
	} else if (prizeType == CHECKPOINT_PRIZE_TYPE_ID) {
		this.prizeType.description = "Checkpoint Prize";
	}
	
	this.numberOfSubmissions = numberOfSubmissions;
};

/**
 * Marathon Match specification class.
 */
com.topcoder.direct.ProjectMMSpecification = function() {
    this.problemId = "-1"
    this.problemName = "";
    this.matchDetails = "";
    this.matchRules = "";
}

/**
 * Project studio specification class.
 */
com.topcoder.direct.ProjectStudioSpecification = function() {
	this.goals = "";
	
	this.targetAudience = "";
	
	this.brandingGuidelines = "";
	
	this.dislikedDesignWebSites = "";
	
	this.otherInstructions = "";
	
	this.winningCriteria = "";
	
	this.submittersLockedBetweenRounds = false;
	
	this.roundOneIntroduction = "";
	
	this.roundTwoIntroduction = "";
	
	this.colors = "";
	
	this.fonts = "";
	
	this.layoutAndSize = "";
	
	this.contestIntroduction = "";
	
	this.contestDescription = "";
};

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
var SOFTWARE_CATEGORY_ID_ASSEMBLY = 14;
var SOFTWARE_CATEGORY_ID_F2F = 38;
var SOFTWARE_CATEGORY_ID_CODE = 39;
var STUDIO_CATEGORY_ID_DESIGN_F2F = 40;
var SOFTWARE_CATEGORY_ID_BUG_HUNT = 9;
var ALGORITHM_CATEGORY_ID_MARATHON = 37;
var STUDIO_IDEA_GENERATION = 22;

var DRAFT_STATUS = "Draft";

var projectCategoryArray = [
	{id:SOFTWARE_CATEGORY_ID_CONCEPT,      name:'CONCEPTUALIZATION',                label:'Software Conceptualization',         typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: true},
	{id:SOFTWARE_CATEGORY_ID_SPEC,         name:'SPECIFICATION',                    label:'Software Specification',             typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: true},
	{id:7,                                 name:'ARCHITECTURE',                     label:'Architecture',                       typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: false},
	{id:SOFTWARE_CATEGORY_ID_DESIGN,       name:'DESIGN',                           label:'Component Design',                   typeId:1, typeName:'Component',   hasMulti:false, hideInDropdown: true},
	{id:SOFTWARE_CATEGORY_ID_DEVELOPMENT,  name:'DEVELOPMENT',                      label:'Component Development',              typeId:1, typeName:'Component',   hasMulti:false, hideInDropdown: true},
	{id:25,                                name:'RIACOMPONENT',                     label:'RIA Component',                      typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: true},
	{id:24,                                name:'RIABUILD',                         label:'RIA Build',                          typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: true},
	{id:19,                                name:'UIPROTOTYPE',                      label:'UI Prototype',                       typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: false},
	{id:SOFTWARE_CATEGORY_ID_ASSEMBLY,     name:'SOFTWARE ASSEMBLY',                label:'Software Assembly',                  typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: false},
	{id:13,                                name:'TESTSUITES',                       label:'Test Suites',                        typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: false},
	{id:26,                                name:'TESTSCENARIOS',                    label:'Test Scenarios',                     typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: true},
	{id:29,                                name:'Copilot Posting',                  label:'Copilot Posting',                    typeId:2, typeName:'Application', hasMulti:false, hideInDropdown: true},
	{id:SOFTWARE_CATEGORY_ID_CONTENT,      name:'Content Creation',                 label:'Content Creation',                   typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: true},
	{id:17,                                name:'Web Design',                       label:'Web Design',                         typeId:3, typeName:'Studio',      hasMulti:true,  hideInDropdown: false},
	{id:STUDIO_CATEGORY_ID_DESIGN_F2F,     name:'Design First2Finish',              label:'Design First2Finish',                typeId:3, typeName:'Studio',      hasMulti:false, hideInDropdown: false},
	{id:20,                                name:'Logo Design',                      label:'Logo Design',                        typeId:3, typeName:'Studio',      hasMulti:true,  hideInDropdown: true},
	{id:16,                                name:'Banners/Icons',                    label:'Banners/Icons',                      typeId:3, typeName:'Studio',      hasMulti:true,  hideInDropdown: true},
	{id:32,                                name:'Application Front-End Design',     label:'Application Front-End Design',       typeId:3, typeName:'Studio',      hasMulti:true,  hideInDropdown: false},
	{id:30,                                name:'Widget or Mobile Screen Design',   label:'Widget or Mobile Screen Design',     typeId:3, typeName:'Studio',      hasMulti:true,  hideInDropdown: false},
	{id:31,                                name:'Front-End Flash',                  label:'Front-End Flash',                    typeId:3, typeName:'Studio',      hasMulti:true,  hideInDropdown: true},
	{id:21,                                name:'Print/Presentation',               label:'Print/Presentation',                 typeId:3, typeName:'Studio',      hasMulti:true,  hideInDropdown: false},
	{id:34,                                name:'Studio Other',                     label:'Studio Other',                       typeId:3, typeName:'Studio',      hasMulti:true,  hideInDropdown: true},
	{id:18,                                name:'Wireframes',                       label:'Wireframes',                         typeId:3, typeName:'Studio',      hasMulti:true,  hideInDropdown: false},
	{id:22,                                name:'Idea Generation',                  label:'Idea Generation',                    typeId:3, typeName:'Studio',      hasMulti:true,  hideInDropdown: false},
	{id:36,                                name:'REPORTING',                        label:'Reporting',                          typeId:2, typeName:'Application', hasMulti:true,  hideInDropdown: true},
	{id:ALGORITHM_CATEGORY_ID_MARATHON,    name:'Marathon Match',                   label:'Marathon Match',                     typeId:2, typeName:'Application', hasMulti:false, hideInDropdown: false},
	{id:SOFTWARE_CATEGORY_ID_BUG_HUNT,     name:'Bug Hunt',                         label:'Bug Hunt',                           typeId:2, typeName:'Application', hasMulti:false, hideInDropdown: false},
	{id:SOFTWARE_CATEGORY_ID_F2F,          name:'First2Finish',                     label:'First2Finish',                       typeId:2, typeName:'Application', hasMulti:false, hideInDropdown: false},
	{id:SOFTWARE_CATEGORY_ID_CODE,         name:'Code',                             label:'Code',                               typeId:2, typeName:'Application', hasMulti:false, hideInDropdown: false}
];

/**
 * Checks whether the project category can be multiround type.
 * 
 * @param categoryId the project category id
 * @returns {Boolean} true if the category contest can be multiround type, false otherwise
 */
function hasMultiRound(categoryId) {
	for (var i = 0; i < projectCategoryArray.length; i++) {
		if (projectCategoryArray[i].id == categoryId && projectCategoryArray[i].hasMulti) {
			return true;
		}
	}
	return false;
}

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
 *
 * Version 1.0.2 Changes: add copilotUserId and copilotUserName
 */
com.topcoder.direct.SoftwareCompetition = function() {	  
	this.assetDTO = new com.topcoder.direct.AssetDTO();
	
    this.projectHeader = new com.topcoder.direct.Project();    
    
    // holding the paid fee
    this.paidFee = 0;
    
    this.multiRound = false;
    
    this.checkpointDate = null;

    // hold the copilot user id
    this.copilotUserId = 0;

    // hold the copilot user handle
    this.copilotUserName = "";
	
	// hold the copilot cost
    this.copilotCost = 0.0;

    // hold the project milestone id  the contest associates with
    this.projectMilestoneId = 0;

    // hold the project milestone name  the contest associates with
    this.projectMilestoneName = 0;
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
	  
	  this.projectStudioSpecification = new com.topcoder.direct.ProjectStudioSpecification();
	  
	  this.projectMMSpecification = new com.topcoder.direct.ProjectMMSpecification();
	  
	  this.prizes = [];
	  
	  this.projectCopilotTypes = [];
	  
	  this.copilotContestExtraInfos = [];

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

      this.isDrOn = function() {
          return this.properties['Digital Run Flag'] ? (this.properties['Digital Run Flag'].toUpperCase() == 'ON') : false;
      }

      this.turnDRFlagOn = function() {
          this.properties['Digital Run Flag'] = "On";
      }

      this.turnDRFlagOff = function() {
          this.properties['Digital Run Flag'] = "Off";
      }

	  this.setCheckpointBonusCost = function(checkpointBonusCost) {
	  	  this.properties['Checkpoint Bonus Cost'] = checkpointBonusCost;
	  }

	  this.getCheckpointBonusCost = function() {
	  	  return this.properties['Checkpoint Bonus Cost'];
	  }

	  this.setAdminFee = function(adminFee) {
	  	  this.properties['Admin Fee'] = adminFee;
          }
	  
	  this.getAdminFee = function() {
	  	  return this.properties['Admin Fee'];
	  }
      
	  this.setContestFeePercentage = function(contestFeePercentage) {
	  	  this.properties['Contest Fee Percentage'] = contestFeePercentage;
	  }
	  
	  this.getContestFeePercentage = function() {
	  	  return this.properties['Contest Fee Percentage'];
	  }

	  this.setSpecReviewCost = function(specReviewCost) {
	  	  this.properties['Spec Review Cost'] = specReviewCost;
	  }

	  this.getSpecReviewCost = function() {
	  	  return this.properties['Spec Review Cost'];
	  }
	  
      this.setCopilotCost = function(copilotCost) {
	  	  this.properties['Copilot Cost'] = copilotCost;
	  }

	  this.getCopilotCost = function() {
	  	  return this.properties['Copilot Cost'];
	  }

	  this.setCostLevel = function(costLevel) {
	  	 // 'A', 'B', 'C', 'M'
	  	  this.properties['Cost Level'] = costLevel;
	  }

	  this.getCostLevel = function() {
	  	  return this.properties['Cost Level'];
	  }
	  
	  this.setAllowStockArt = function(allowStockArt) {
	  	  this.properties['Allow Stock Art'] = allowStockArt;
	  }

	  this.getAllowStockArt = function() {
	  	  return this.properties['Allow Stock Art'];
	  }
	  
	  this.setMaximumSubmissions = function(maximumSubmissions) {
	  	  this.properties['Maximum Submissions'] = maximumSubmissions;
	  }

	  this.getMaximumSubmissions = function() {
	  	  return this.properties['Maximum Submissions'];
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
 * Project copilot type class.
 */
com.topcoder.direct.ProjectCopilotType = function(id, name, description) {
    this.id = id;
    
    this.name = name;
    
    this.description = description;
}

/**
 * Copilot contest extra info type class.
 */
com.topcoder.direct.CopilotContestExtraInfoType = function(id, name, description) {
    this.id = id;
    
    this.name = name;
    
    this.description = description;
}

var BUDGET_EXTRA_INFO_TYPE = new com.topcoder.direct.CopilotContestExtraInfoType(1, 'Budget', 'Budget');
var OTHER_EXTRA_INFO_TYPE = new com.topcoder.direct.CopilotContestExtraInfoType(2, 'Other Managing Experience', 'Other Managing Experience');

/**
 * Copilot contest extra info class.
 */
com.topcoder.direct.CopilotContestExtraInfo = function(type, value) {
    this.type = type;
    
    this.value = value;
}

/**
 * Main Widget
 */
com.topcoder.direct.MainWidget = function() {
  // allowStockArt
  this.allowStockArt = true;

  //'STUDIO', 'SOFTWARE', or 'ALGORITHM'
  this.competitionType = null;
  
  // software competition
  this.softwareCompetition = new com.topcoder.direct.SoftwareCompetition();
  
  this.isStudioContest = function() {
  	 return 'STUDIO' == this.competitionType;
  }
  
  this.isSoftwareContest =  function() {
  	 return 'SOFTWARE' == this.competitionType;
  }
  
  this.isAlgorithmContest =  function() {
     return 'ALGORITHM' == this.competitionType;
  }
}

