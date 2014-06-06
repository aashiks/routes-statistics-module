/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function batchNamesfromBatchArray(batchStatusObjectArray){
     console.log("Batch size " + batchStatusObjectArray.length);
     for(i=0;i<batchStatusObjectArray.length;i++){
         var b = batchStatusObjectArray[i];
         var hourDisplay = calculateHourDisplay(b.startTimeChar,b.endTimeChar );
         // now generate the row
          var rowstring = "<tr> \n" +
                          "<td>" + b.batchName + "</td> \n" ;
          for(var i = 0 ; i < 24 ; i++ ) {
              if(i== hourDisplay.startFrom){
                  rowstring = rowstring + 
                                "<td><span class=\"label label-success\">&nbsp;</span></td> \n";

              }else{
                  rowstring =  rowstring + 
                          " <td><span>&nbsp;</span></td>\n";
              }
          }
          rowstring =  rowstring + "</tr>\n";
     }
}

function calculateHourDisplay(startTime,endTime) { //calculate in steps of fifteen minutes
    // format : "YYYY-MM-DD HH:mm:ss" entries in moment.js format
    var startMoment = moment(startTime, "YYYY-MM-DD HH:mm:ss");
    var endMoment = moment(endTime, "YYYY-MM-DD HH:mm:ss");

    var timeDiff=endMoment.diff(startMoment,'minutes');
    var hours = 0;
    var minutesDisplay = 0;
    if (timeDiff > 60){
        // find out how many hours the batch ran for
        hours = timeDiff % 60;
        timeDiff = (timeDiff - (hours*60));
    }
    if(timeDiff < 15 ){
        minutesDisplay = 25;
    }else if(timeDiff < 30){
       minutesDisplay = 50; 
    }else if(timeDiff < 45){
        minutesDisplay = 75;
    }
    var displayWidth= (hours* 100 )+ minutesDisplay;
    // find out what hour to start from 
    startMoment.hour();    
    return {
      'startFrom' : startMoment.hour(),
      'displayWidth' : displayWidth
    };
}