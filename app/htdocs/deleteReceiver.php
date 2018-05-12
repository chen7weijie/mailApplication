<?php
include_once("e_receiver.php");
$name=$_POST['receiverName'];
$affectRow=Receiver::where('name','=',$name)->delete();
echo "success";
?>