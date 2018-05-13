<?php
$servername = "localhost";
$username = "root";
$password = "cwj727834048";
$dbname = "email_send_system";
$groupName=$_POST['groupName'];
$name=$_POST['name'];
$email=$_POST['email'];
$sex=$_POST['sex'];
$des=$_POST['des'];
$conn = mysqli_connect($servername, $username, $password, $dbname);
// 检测连接
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$sql = "select * from e_group where name='$groupName'";
$result=mysqli_query($conn, $sql);
$row=$result->fetch_object();
$id=$row->id;
$insertsql = "INSERT INTO e_receiver (name, email, sex, phone_number,group_id)
VALUES ('$name','$email','$sex','$des','$id')";

if (mysqli_query($conn, $insertsql)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
?>