import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/insurance_bloc.dart';
import '../models/insurance.dart';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/insurance_bloc.dart';
import '../models/insurance.dart';

class AddEditInsuranceScreen extends StatefulWidget {
  final Insurance? insurance; // If null, it's for adding new insurance, otherwise editing

  const AddEditInsuranceScreen({super.key, this.insurance});

  @override
  _AddEditInsuranceScreenState createState() => _AddEditInsuranceScreenState();
}

class _AddEditInsuranceScreenState extends State<AddEditInsuranceScreen> {
  final _formKey = GlobalKey<FormState>();

  // Form controllers
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _countryController = TextEditingController();
  final TextEditingController _annualPriceController = TextEditingController();

  @override
  void initState() {
    super.initState();
    if (widget.insurance != null) {
      // If insurance is passed, it means we are editing
      _nameController.text = widget.insurance!.name;
      _countryController.text = widget.insurance!.country;
      _annualPriceController.text = widget.insurance!.annualPrice.toString();
    }
  }

  @override
  void dispose() {
    _nameController.dispose();
    _countryController.dispose();
    _annualPriceController.dispose();
    super.dispose();
  }

  void _saveInsurance() {
    if (_formKey.currentState!.validate()) {
      final String name = _nameController.text;
      final String country = _countryController.text;
      final double annualPrice = double.parse(_annualPriceController.text);

      final Insurance insurance = Insurance(
        id: widget.insurance?.id, 
        name: name,
        country: country,
        hospitalsCovered: [],
        annualPrice: annualPrice,
      );

      if (widget.insurance == null) {
        // Add new insurance
        BlocProvider.of<InsuranceBloc>(context).add(AddInsurance(insurance));
      } else {
        // Update existing insurance
        BlocProvider.of<InsuranceBloc>(context).add(UpdateInsurance(insurance));
      }

      Navigator.pop(context); // Close the bottom sheet
    }
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: MediaQuery.of(context).viewInsets, 
      child: Container(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextFormField(
                controller: _nameController,
                decoration: const InputDecoration(labelText: 'Insurance Name'),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter a name';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _countryController,
                decoration: const InputDecoration(labelText: 'Country'),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter a country';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _annualPriceController,
                decoration: const InputDecoration(labelText: 'Annual Price'),
                keyboardType: TextInputType.number,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter the annual price';
                  }
                  return null;
                },
              ),
              const SizedBox(height: 16.0),
              ElevatedButton(
                onPressed: _saveInsurance,
                child: Text(widget.insurance == null
                    ? 'Add Insurance'
                    : 'Update Insurance'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
