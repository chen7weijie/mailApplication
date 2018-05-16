<?php
include_once("e_address.php");
$address=Address::where('is_using', '=','0')->get();
echo json_encode($address);
?>