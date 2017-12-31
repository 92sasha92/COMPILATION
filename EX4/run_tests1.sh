#!/bin/bash
BASEDIR           = $(shell pwd)
OUTPUT_DIR        = ${BASEDIR}/FOLDER_6_EXPECTED_OUTPUT/TEST_01_Print_Primes_Expected_Output.txt
java -jar PARSER ./FOLDER_4_INPUT/TEST_01_Print_Primes.txt ./FOLDER_5_OUTPUT/t1.txt
diff ./FOLDER_5_OUTPUT/t1.txt ${OUTPUT_DIR}
