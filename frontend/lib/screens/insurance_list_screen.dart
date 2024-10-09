import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/insurance_bloc.dart';
import '../models/insurance.dart';
import 'add_edit_insurance_screen.dart';


class InsuranceListScreen extends StatelessWidget {
  const InsuranceListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Insurance List')),
      body: BlocBuilder<InsuranceBloc, InsuranceState>(
        builder: (context, state) {
          if (state is InsuranceInitial) {
            BlocProvider.of<InsuranceBloc>(context).add(FetchInsurances());
            return const Center(child: CircularProgressIndicator());
          } else if (state is InsuranceLoading) {
            return const Center(child: CircularProgressIndicator());
          } else if (state is InsuranceLoaded) {
            return ListView.builder(
              itemCount: state.insurances.length,
              itemBuilder: (context, index) {
                Insurance insurance = state.insurances[index];
                return ListTile(
                  title: Text(insurance.name),
                  subtitle: Text(insurance.country),
                  trailing: Text('\$${insurance.annualPrice.toStringAsFixed(2)}'),
                  onTap: () {
                    showModalBottomSheet(
                      context: context,
                      isScrollControlled: true,
                      builder: (BuildContext context) {
                        return AddEditInsuranceScreen(insurance: insurance);
                      },
                    );
                  },
                );
              },
            );
          } else if (state is InsuranceError) {
            return Center(child: Text('Error: ${state.message}'));
          }
          return Container();
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          showModalBottomSheet(
            context: context,
            isScrollControlled: true,
            builder: (BuildContext context) {
              return const AddEditInsuranceScreen();
            },
          );
        },
        child: const Icon(Icons.add),
      ),
    );
  }
}
