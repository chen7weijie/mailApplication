<?php
$con = mysqli_connect("localhost","root","cwj727834048","mvctest");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }
$sql = "INSERT INTO user
VALUES (2, 'Doe', 'john')";

if ($con->query($sql) === TRUE) {
    echo "新记录插入成功";
} else {
    echo "Error: " . $sql . "<br>" . $con->error;
}
//$result=mysqli_query($con,"SELECT * FROM user");
//$row=mysqli_fetch_assoc($result);
//echo json_encode($row);
mysqli_close($con);
?>