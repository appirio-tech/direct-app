/*
* Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
*/
package com.topcoder.admin.scorecards

/**
* This class is a <code>Domain</code> class for id_sequences table. It's just for test purpose.
*
* @author TCSASSEMBER
* @version 1.0
*/
class IDSequences {
    /**
     * Represents the name of the id sequence.
     */
    String name
    
    /**
     * Represents the next block start of the id sequence.
     */
    BigDecimal nextBlockStart
    
    /**
     * Represents the block size of the id sequence.
     */
    BigDecimal blockSize
    
    /**
     * A flag indicates whether the id sequence is exhausted.
     */
    Boolean exhausted
    
    /**
    * Define the validation rules
    */
    static constraints = {
    }
    
    /**
    * Define the ORM mapping.
    */
   static mapping = {
       table 'id_sequences'
       columns {
           nextBlockStart column:'next_block_start'
           blockSize column:'block_size'
       }
    }
}
