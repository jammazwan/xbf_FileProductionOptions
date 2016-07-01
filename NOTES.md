### FileProductionOptions NOTES:

This project takes a departure from the normal approach for other x__projects. Spring was entirely removed as a dependency, and each test stands alone, not requiring any other artifacts - each contains it's own RouteBuilder.

See [this Camel doc](http://camel.apache.org/testing) for a sample of where this testing style came from.

_The motivation for this approach?_ Each test needed it's own route, many tests were required, and doing this in the same way as other x__project tests with Spring and separate RouterBuilder files could be quite confusing.

In another break with convention, I placed the route at top, and test below it, as the route is the main interesting point of the test. It looks just "wrong". But it is easy to read.

### Test Naming Conventions

ASpecificDirectoryAnyFileTest - example.

 * First charachter A-Z to order tests easiest to hardest
 * Then CamelCase test function description
 * Then Test

### AWriteToFileTest

* Writes the simplest file in the simplest way
* Validates that the file was written, and has the expected content

### BOverWriteFileTest

 * Writes a file with first content
 * Writes over that file with second content
 * Writes over that file with third content, but uses fileExist=Ignore
 * Verifies that only the second content was written

### CAppendFileTest

 * Writes a file with first content
 * Writes same file with second content, using fileExist=Append
 * Verifies that file exists with second content appended to first
 
### DMoveOldFileTest

 * Writes a file with the first content - now called the "old" file
 * Verifies through mock that this message was actually processed using a message count
 * Writes over that file, using fileExist=Move&moveExisting=previous
 * Verifies that the old file is now in the previous folder and has the old file content
 * Verifies that the new file is now in the original location of the old file
 
### EDoneFileNameTest

 * Writes a file using doneFileName=${file:name}.done
 * Verifies that the done file was written at the same time as the file itself
 
### FAllowNullBodyFileTest

_This code was stolen directly and shamelessly from camel-core's FileProducerAllowNullBodyTest_

 * Writes a file with null body and without allowNullBody=true
 * Verifies that exception was thrown
 * Writes same file successfully with allowNullBody=true
 * Verifies that file was written successfully


