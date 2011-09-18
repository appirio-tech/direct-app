/*
* Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
*/
import grails.util.GrailsUtil

import com.topcoder.admin.scorecards.IDSequences
import com.topcoder.database.utilities.TopCoderTableHiLoGenerator

class BootStrap {

	def init = { servletContext ->
        switch (GrailsUtil.environment) {
            case "test":
                TopCoderTableHiLoGenerator.TEST_ENV = true
                // insert id sequence for scorecard_id_seq
                def scorecardIdSeq = new IDSequences(
                    name: 'scorecard_id_seq',
                    nextBlockStart: 10000,
                    blockSize: 100,
                    exhausted: false
                    )
                scorecardIdSeq.save()
                if (scorecardIdSeq.hasErrors()) {
                    println scorecardIdSeq.errors
                }
                
                // insert id sequence for scorecard_group_id_seq
                def scorecardGroupIdSeq = new IDSequences(
                    name: 'scorecard_group_id_seq',
                    nextBlockStart: 10000,
                    blockSize: 100,
                    exhausted: false
                    )
                scorecardGroupIdSeq.save()
                if (scorecardGroupIdSeq.hasErrors()) {
                    println scorecardGroupIdSeq.errors
                }
                
                // insert id sequence for scorecard_question_id_seq
                def scorecardQuestionIdSeq = new IDSequences(
                    name: 'scorecard_question_id_seq',
                    nextBlockStart: 10000,
                    blockSize: 100,
                    exhausted: false
                    )
                scorecardQuestionIdSeq.save()
                if (scorecardQuestionIdSeq.hasErrors()) {
                    println scorecardQuestionIdSeq.errors
                }
                
                // insert id sequence for scorecard_section_id_seq
                def scorecardSectionIdSeq = new IDSequences(
                    name: 'scorecard_section_id_seq',
                    nextBlockStart: 10000,
                    blockSize: 100,
                    exhausted: false
                    )
                scorecardSectionIdSeq.save()
                if (scorecardSectionIdSeq.hasErrors()) {
                    println scorecardSectionIdSeq.errors
                }
                break;
        }
	}
	def destroy = {
	}
}
