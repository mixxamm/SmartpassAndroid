
$array = array();
while($row = mysql_fetch_assoc($query)){
    $array[] = $row;
}
echo json_encode($array);