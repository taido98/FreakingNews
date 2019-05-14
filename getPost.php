<?php 
    include_once "dbconnect.php";

    $category = $_GET["category"];
    $idUser = $_GET["idUser"];

    if($category == "Tất cả"){
        $sql = "SELECT p.*, t.topic AS topicName, u.name AS name, u.url_avatar AS url_avatar,
            SUM(v.status) AS vote,
            (SELECT status FROM votes 
            WHERE idUser = $idUser AND idPost = p.id) AS status
            FROM posts p JOIN users u ON p.idUser = u.id 
            JOIN topics t ON t.id = p.idTopic
            LEFT JOIN votes v ON v.idPost = p.id
            GROUP BY p.id
            ORDER BY vote ASC";
        $result = $connect->query($sql);
        $data = array();

        if(!$result){
            die($connect->error);
        }
        if($result->num_rows > 0){
            while($rows = $result->fetch_assoc()){
                $data[] = $rows;
            }
        }
        echo json_encode($data);
        // echo '<script>console.log('.json_encode($data).')</script>';
    }
    // echo '<script>console.log('.$category.')</script>';

    else {
        $sql = "SELECT p.*, t.topic AS topicName, u.name AS name, u.url_avatar AS url_avatar,
            SUM(v.status) AS vote,
            (SELECT status FROM votes 
            WHERE idUser = $idUser AND idPost = p.id) AS status
            FROM posts p JOIN users u ON p.idUser = u.id 
            JOIN topics t ON t.id = p.idTopic
            LEFT JOIN votes v ON v.idPost = p.id
            WHERE t.topic LIKE '$category'
            GROUP BY p.id
            ORDER BY vote ASC";
        $result = $connect->query($sql);
        $data = array();

        if(!$result){
            die($connect->error);
        }
        if($result->num_rows > 0){
            while($rows = $result->fetch_assoc()){
                $data[] = $rows;
            }
        }
        echo json_encode($data);
        // echo '<script>console.log('.json_encode($data).')</script>';
    }

    $connect->close();
?>


[{"url":"http://192.168.0.109/FreakingNews/upload/photo-3-1535416390418891808922.png"},
{"url":"http://hinhanhdephd.com/wp-content/uploads/2016/01/tai-hinh-girl-xinh-lam-avatar-de-thuong-nhat-22.jpg"}]