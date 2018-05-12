<?php
$servername = "localhost";
$username = "root";
$password = "cwj727834048";
$dbname = "email_send_system";
 
// 创建连接
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
} 
 
$sql = "SELECT * FROM test";
$result = $conn->query($sql);
 
if ($result->num_rows > 0) {
    // 输出数据
    while($row = $result->fetch_assoc()) {
        var_dump($row["send_date"]);
    }
} else {
    echo "0 结果";
}
$conn->close();
?>