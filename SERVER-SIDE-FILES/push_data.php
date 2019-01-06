<?php
	require 'db_conn.php';



	if (isset($_POST['add_hostel_data']) && isset($_POST['hostel_data']))  //for adding hostel
	{
		$hostelObj = json_decode($_POST['hostel_data'], true);
		$hostelObj['hostel_img'] = json_encode($hostelObj['hostel_img']);

		$query = "INSERT INTO hostel_table (hostel_name, hostel_address, hostel_city, hostel_extras, hostel_rating, hostel_rooms, hostel_floors, hostel_owner_email, hostel_img)
								   VALUES ('".$hostelObj['hostelName']."', '".$hostelObj['hostelAddress']."', '".$hostelObj['hostelCity']."', '".$hostelObj['hostelExtras']."', '".$hostelObj['rating']."', '".$hostelObj['no_rooms']."', '".$hostelObj['no_floors']."', '".$hostelObj['owner_email']."', '".$hostelObj['hostel_img']."');";
		$result = mysqli_query($conn, $query);

		echo json_encode($result);
	}