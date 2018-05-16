<?php
$servername = "localhost";
$username = "root";
$password = "cwj727834048";
$dbname = "email_send_system";
$stringId=$_POST['addressId'];
$id=(int)$stringId;
$conn = mysqli_connect($servername, $username, $password, $dbname);
// 检测连接
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
$sql = "update email_address set is_using=1 where id='$id'";
$sql2="update email_address set is_using=0 where id!='$id'";
if (mysqli_query($conn, $sql)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
if (mysqli_query($conn, $sql2)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
?>