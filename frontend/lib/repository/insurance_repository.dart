import '../models/insurance.dart';
import '../services/api_service.dart';

class InsuranceRepository {
  final ApiService _apiService = ApiService();

  Future<List<Insurance>> getInsurances() async {
    return await _apiService.getInsurances();
  }

  Future<Insurance> addInsurance(Insurance insurance) async {
    return await _apiService.addInsurance(insurance);
  }

  Future<void> updateInsurance(Insurance insurance) async {
    return await _apiService.updateInsurance(insurance);
  }
}
