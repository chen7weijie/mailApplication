<?php
$servername = "localhost";
$username = "root";
$password = "cwj727834048";
$dbname = "email_send_system";
$postId=$_POST['id'];
$postStatus=$_POST['status'];
$id=(int)$postId;
$status=(int)$postStatus;
var_dump($id);
var_dump($status);
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
if($status==1){
	$sql="update task set status=0 where id='$id'";
}
else{
	$sql="update task set status=1 where id='$id'";
}
if (mysqli_query($conn, $sql)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);

?>