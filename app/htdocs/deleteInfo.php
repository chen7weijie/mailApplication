<?php
$servername = "localhost";
$username = "root";
$password = "cwj727834048";
$dbname = "email_send_system";
$infoId=$_POST['infoId'];
$id=(int)$infoId;
$conn = mysqli_connect($servername, $username, $password, $dbname);
// 检测连接
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
$sql = "delete from information where id='$id'";

if (mysqli_query($conn, $sql)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
?>