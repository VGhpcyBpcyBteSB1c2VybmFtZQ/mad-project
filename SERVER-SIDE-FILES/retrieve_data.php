<?php
	require 'db_conn.php';


	/*$q = "insert into user_table (user_email, user_pwd, account_type)
						VALUES('test@test.com', '\$2y\$10\$RxbVC2fOqkCtjr1OKciJz.M5swXs6MmiAaNsGjBHxaOwyh5j/2juK', false)";
	mysqli_query($conn, $q);

	/*$q = "insert into hostel_table (hostel_name, hostel_city, hostel_address, hostel_rooms, hostel_floors, hostel_extras, hostel_owner_email, hostel_rating)
						VALUES('h1', 'lahore', 'st 64', 20, 20, 'AC, fridge', 'test@test.com', 3.3)";
	mysqli_query($conn, $q);*/

	if (isset($_POST['get_all_hostel_data']))  //for fetching all hostels data
	{
		$final_result = array("hostelsStored" => array());

		$query = "SELECT * FROM hostel_table";
		$result = mysqli_query($conn, $query);

		for ($var = 0; $var < mysqli_num_rows($result); $var++)
		{
			$row = mysqli_fetch_assoc($result);
			$hostel_obj = array(
				"hostelName" => $row["hostel_name"],
				"hostelAddress" => $row["hostel_address"],
				"hostelCity" => $row["hostel_city"],
				"hostelExtras" => $row["hostel_extras"],
				"rating" => $row["hostel_rating"],
				"no_rooms" => $row["hostel_rooms"],
				"no_floors" => $row["hostel_floors"],
				"owner_email" => $row["hostel_owner_email"],
				"hostel_id" => $row["hostel_id"],
				"hostel_img" => $row["hostel_img"]
			);

			array_push($final_result["hostelsStored"], $hostel_obj);
		}

		echo json_encode($final_result);
	}
	else if (isset($_POST['verify_user_data']) && isset($_POST['email']) && isset($_POST['pwd']))  //for verifying user
	{
		$email = mysqli_real_escape_string($conn, ucwords(strtolower($_POST['email'])));
		$email = str_replace(" ", "", $email);

		$query = "SELECT user_pwd FROM user_table WHERE user_email = '$email'";
		$result = mysqli_query($conn, $query);
		if (mysqli_num_rows($result) == 1)
		{
			$row = mysqli_fetch_assoc($result);
			if (password_verify($_POST['pwd'], $row['user_pwd']))
				echo "True";
			else
				echo "False";
		}
		else
			echo "False";
	}
	else if (isset($_POST['get_user_data']) && isset($_POST['email']) && isset($_POST['pwd']))  //for fetching user data
	{
		$email = mysqli_real_escape_string($conn, ucwords(strtolower($_POST['email'])));
		$email = str_replace(" ", "", $email);

		$query = "SELECT * FROM user_table WHERE user_email = '$email'";
		$result = mysqli_query($conn, $query);
		if (mysqli_num_rows($result) == 1)
		{
			$row = mysqli_fetch_assoc($result);
			if (password_verify($_POST['pwd'], $row['user_pwd']))
			{
				$struct = array(
					"userName" => "",
					"email" => $row['user_email'],
					"password" => $row['user_pwd'],
					"accountType" => $row['account_type']
				);
				echo json_encode($struct);
			}
			else
			{
				$struct = array(
					"userName" => NULL,
					"email" => NULL,
					"password" => NULL,
					"accountType" => NULL
				);
				echo json_encode($struct);
			}
		}
		else
		{
			$struct = array(
				"userName" => NULL,
				"email" => NULL,
				"password" => NULL,
				"accountType" => NULL
			);
			echo json_encode($struct);
		}
	}