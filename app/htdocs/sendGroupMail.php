<?php
include_once("class.phpmailer.php");
include_once("e_address.php");
include_once("e_receiver.php");
include_once("e_group.php");
$title=$_POST['title'];
$content=$_POST['content'];
$getId=$_POST['id'];
$id=(int)$getId;
//$receiverName=$_POST['receiverName'];//接受参数
$mail=new PHPMailer();
$mail->isSMTP();// 使用SMTP服务
$mail->CharSet = "utf8";// 编码格式为utf8，不设置编码的话，中文会出现乱码
$mail->Host = "smtp.163.com";// 发送方的SMTP服务器地址
$mail->SMTPAuth = true;
$mail->SMTPSecure = "ssl";// 使用ssl协议方式
$mail->Port = 994;// 163邮箱的ssl协议方式端口号是465/994
$from=Address::find(1)->email;
$pwd=Address::find(1)->password;
$receivers=Group::find($id)->receivers;
//$number=count($receivers);
foreach($receivers as $receiver){
	//$to=$receiver->email;
	$mail->addAddress($receiver->email);

}
$mail->Username=$from;
$mail->Password=$pwd;
$mail->setFrom($from,"Mailer");
$mail->addReplyTo("chen7weijie@163.com","Reply");
$mail->Subject = $title;// 邮件标题
$mail->Body = $content;// 邮件正文
if(!$mail->send()){// 发送邮件
    echo "Message could not be sent.";
    echo "Mailer Error: ".$mail->ErrorInfo;// 输出错误信息
}else{
    echo 'Message has been sent.';
}
//var_dump($data);

?>