<?php
$servername = "localhost";
$username = "root";
$password = "cwj727834048";
$dbname = "email_send_system";
$title=$_POST['title'];
$content=$_POST['content'];
$des=$_POST['des'];
$typeid=$_POST['chooseType'];
$id=0;
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
if($typeid=="立即发送"){
	$id=1;
}
else if($typeid=="每周一次"){
	$id=2;
}
else if($typeid=="每日发送"){
	$id=3;
}
else{
	$id=4;
}
$insertsql = "INSERT INTO information (title, content, description, info_type_id)
VALUES ('$title','$content','$des','$id')";

if (mysqli_query($conn, $insertsql)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
?>