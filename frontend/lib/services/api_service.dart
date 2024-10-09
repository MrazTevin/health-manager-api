import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/insurance.dart';
import '../utils/config.dart';

class ApiService {
  Future<List<Insurance>> getInsurances() async {
    final response = await http.get(Uri.parse('${Config.baseUrl}/api/v1/insurance/all'));
    if (response.statusCode == 200) {
      List jsonResponse = json.decode(response.body);
      return jsonResponse.map((data) => Insurance.fromJson(data)).toList();
    } else {
      throw Exception('Failed to load insurances');
    }
  }

  Future<Insurance> addInsurance(Insurance insurance) async {
    final response = await http.post(
      Uri.parse('${Config.baseUrl}/api/v1/insurance/create'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(insurance.toJson()),
    );
    if (response.statusCode == 201) {
      return Insurance.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to add insurance');
    }
  }

  Future<void> updateInsurance(Insurance insurance) async {
    final response = await http.put(
      Uri.parse('${Config.baseUrl}/api/v1/insurance/edit${insurance.id}'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(insurance.toJson()),
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to update insurance');
    }
  }

}