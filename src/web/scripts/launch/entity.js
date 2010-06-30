/**
 * Constants
 */
var CONTEST_STATUS_UNACTIVE_NOT_YET_PUBLISHED = 1;
var CONTEST_STATUS_ACTIVE_PUBLIC = 2 ;
var CONTEST_DETAILED_STATUS_DRAFT =15 ;
var CONTEST_DETAILED_STATUS_ACTIVE_PUBLIC =2 ;
var CONTEST_DETAILED_STATUS_SCHEDULED =9 ;

/**
 * Contest JS Classes
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

com.topcoder.direct.StudioCompetition = function() {
   this.id = -1;

   this.type = null;

   this.contestData = new com.topcoder.direct.ContestData();

   //Dates
   this.startDate = null;

   this.endDate	= null;

   this.milestoneDate = null;

}


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

   this.drpoints = 0;
   
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

com.topcoder.direct.ContestMultiRoundInformationData = function() {
   this.id = -1;

   //this.milestoneDate = null;
   
   this.roundOneIntroduction = null;
   
   this.roundTwoIntroduction = null;
}

com.topcoder.direct.MilestonePrizeData = function() {
   this.id = -1;

   this.amount = 0;

   this.numberOfSubmissions = 0;
}

com.topcoder.direct.PrizeData = function(place, amount) {
   this.place = place;

   this.amount = amount;
}


com.topcoder.direct.MainWidget = function() {
  //'STUDIO' or 'SOFTWARE'
  this.competitionType = null;

  // studio competition
  this.competition = new com.topcoder.direct.StudioCompetition();

  // holding the mileston prize information
  this.milestonePrizeData = new com.topcoder.direct.MilestonePrizeData();
}

