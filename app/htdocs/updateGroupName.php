<?php
$servername = "localhost";
$username = "root";
$password = "cwj727834048";
$dbname = "email_send_system";
$groupId=$_POST['id'];
$name=$_POST['name'];
$id=(int)$groupId;
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
$sql="update e_group set name='$name' where id='$id'";
if (mysqli_query($conn, $sql)) {
    echo "修改成功！";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
?>