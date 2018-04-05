/**
 * 
 */
// 이미지 타입인지 체크하는 function
function checkImageType(fileName){
	
	var pattern = /jpg|gif|png|jpeg/i;
	
	return fileName.match(pattern);
}

function getFileInfo(fullName){
	
	var fileName, imgsrc, getLink;
	
	var fileLink;
	
	if(checkImageType(fullName)){
		imgsrc = "/displayFile?fileName="+fullName;
		fileLink = fullName.substr(14);
		
		var front = fullName.substr(0,12); // /2015/00/00/
		var end = fullName.substr(14);
		
		console.log("front : ", front);
		console.log("end : ", end);
		
		getLink = "/displayFile?fileName=" + front + end;
	}else{
		imgsrc="/resources/dist/img/file.png";
		fileLink = fullName.substr(12);
		getLink = "/displayFile?fileName=" + fullName;
	}
	fileName = fileLink.substr(fileLink.indexOf("_")+1);
	
	return {fileName:fileName, imgsrc:imgsrc, getLink:getLink, fullName:fullName};
	
}