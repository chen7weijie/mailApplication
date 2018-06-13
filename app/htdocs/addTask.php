<?php
$servername = "localhost";
$username = "root";
$password = "cwj727834048";
$dbname = "email_send_system";
$date=$_POST['date'];
$time=$_POST['time'];
$infoid=$_POST['infoId'];
$jsondata=$_POST['jsonData'];
//$groupid=0;
$msgid=(int)$infoid;
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
/*if($groupname=="所有分组"){
	$groupid=0;
}
else{
$sql="select * from e_group where name='$groupname'";
$result=mysqli_query($conn, $sql);
$row=$result->fetch_object();
$groupid=$row->id;
}*/
$de_json=json_decode($jsondata,TRUE);
$count=count($de_json);
for($i=0;$i<$count;$i++){
	$id=$de_json[$i]['id'];
	$insertsql = "INSERT INTO task
VALUES (null,'$id','$msgid','$date','$time',1)";
if (mysqli_query($conn, $insertsql)) {
    //echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
if($i==$count-1){
	echo "success";
}
	//var_dump($id);
}


mysqli_close($conn);
?>