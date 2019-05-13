<?php
include_once 'dbConnect.php';    
session_start();
$email =$_POST['email'];
$name =$_POST['name'];
$url_avatar = $_POST['url'];

$result = mysqli_query($connect,"SELECT `id` FROM `users` WHERE email='$email'" );
/*trong android studio khi dang nhap gui len email ah ?
Cái này là sét xem có email trùng chưa
thế khi đăng nhập từ facebook hay google thì sẽ gửi lên server cái gì?
Gửi lên name, email, url_avatar
ok
*/
if($result->num_rows == 0){
    $query = "INSERT INTO users VALUES(null, '$email', '$name', '$url_avatar')";
    if(mysqli_query($connect, $query)){
        $result = mysqli_query($connect,"SELECT `id` FROM `users` WHERE email='$email'" );
        while($rows = $result->fetch_assoc()){
            $idUser = $rows["id"];
        }
        echo $idUser;
    }
    else{
        echo "error @@@";
    }

}
else{
    while($rows = $result->fetch_assoc()){
        $idUser = $rows["id"];
    }
    echo $idUser;
}

$connect->close();

?>