what I have done.


1. source code and test cases are updated, to use generic type, it will make the code more clean.
2. some redundancy code is removed, like 

---------------------------
        Long id = new Long(-1);

        if (uploadType != null) {
            id = new Long(uploadType.getId());
        }
--------------------------------
        Long id = new Long(-1);

        if (uploadStatus != null) {
            id = new Long(uploadStatus.getId());
        }
----------------------------------

are replaced by getId(IdentifiableDeliverableStructure) method.
3. since Java 1.5, we can use the autoboxing feature, see http://download.oracle.com/javase/1.5.0/docs/guide/language/autoboxing.html, so code like *new Long(id)*, can be simply *id*, it is done in my submission.
4. please check each class's documentation for concrete change in version 1.2.

