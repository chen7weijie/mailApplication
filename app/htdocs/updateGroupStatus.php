<?php
$servername = "localhost";
$username = "root";
$password = "cwj727834048";
$dbname = "email_send_system";
$groupId=$_POST['id'];
$groupStatus=$_POST['status'];
$id=(int)$groupId;
$status=(int)$groupStatus;
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
if($status==1){
	$sql="update e_group set status=0 where id='$id'";
}
else{
	$sql="update e_group set status=1 where id='$id'";
}


if (mysqli_query($conn, $sql)) {
    echo "修改成功！";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
?>