<?php
$servername = "localhost";
$username = "root";
$password = "cwj727834048";
$dbname = "email_send_system";
$infoId=$_POST['id'];
$title=$_POST['title'];
$content=$_POST['content'];
$id=(int)$infoId;
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}
$sql="update information set title='$title',content='$content' where id='$id'";
if (mysqli_query($conn, $sql)) {
    echo "success!";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);

?>