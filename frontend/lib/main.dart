import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'blocs/insurance_bloc.dart';

import 'repository/insurance_repository.dart';
import 'screens/insurance_list_screen.dart';
void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
   const MyApp({super.key});
   
  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider(create: (context) => InsuranceBloc(InsuranceRepository())),
       
      ],
      child: MaterialApp(
        title: 'Health Insurance App',
        theme: ThemeData(primarySwatch: Colors.blue),
        home: const InsuranceListScreen(),
      ),
    );
  }
}