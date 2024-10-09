import 'package:flutter_bloc/flutter_bloc.dart';
import '../models/insurance.dart';
import '../repository/insurance_repository.dart';

abstract class InsuranceEvent {}
class FetchInsurances extends InsuranceEvent {}
class AddInsurance extends InsuranceEvent {
  final Insurance insurance;
  AddInsurance(this.insurance);
}

class UpdateInsurance extends InsuranceEvent {
  final Insurance insurance; 
  UpdateInsurance(this.insurance);
}

abstract class InsuranceState {}
class InsuranceInitial extends InsuranceState {}
class InsuranceLoading extends InsuranceState {}
class InsuranceLoaded extends InsuranceState {
  final List<Insurance> insurances;
  InsuranceLoaded(this.insurances);
}
class InsuranceError extends InsuranceState {
  final String message;
  InsuranceError(this.message);
}

class InsuranceBloc extends Bloc<InsuranceEvent, InsuranceState> {
  final InsuranceRepository repository;

  InsuranceBloc(this.repository) : super(InsuranceInitial()) {
    on<FetchInsurances>((event, emit) async {
      emit(InsuranceLoading());
      try {
        final insurances = await repository.getInsurances();
        emit(InsuranceLoaded(insurances));
      } catch (e) {
        emit(InsuranceError(e.toString()));
      }
    });

    on<AddInsurance>((event, emit) async {
      emit(InsuranceLoading());
      try {
        await repository.addInsurance(event.insurance);
        final insurances = await repository.getInsurances();
        emit(InsuranceLoaded(insurances));
      } catch (e) {
        emit(InsuranceError(e.toString()));
      }
    });

    on<UpdateInsurance>((event, emit) async {
        emit(InsuranceLoading());
        try {
          await repository.updateInsurance(event.insurance);
          final insurances = await repository.getInsurances();
          emit(InsuranceLoaded(insurances));
        } catch (e) {
          emit(InsuranceError(e.toString()));
        }
      });
      


  }
}