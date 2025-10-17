package com.etiya.customerservice.service.messages;

public class Messages {
    public static final String NationalIdentityExists = "NationalIdentityExists";
    public static final String BillingAccountBoundingNotExists = "BillingAccountBoundingNotExists";
    public static final String TypeCannotChange =  "TypeCannotChange";
    public static final String AlreadyClosedAccount = "AlreadyClosedAccount";
    public static final String ActiveAccountCannotDeleted = "ActiveAccountCannotDeleted";
    public static final String CityAlreadyExists = "CityAlreadyExists";
    public static final String BillingAccountExistsInAddress = "BillingAccountExistsInAddress";
    public static final String CustomerNotExists =  "CustomerNotExists";
    public static final String InactiveCustomer =  "InactiveCustomer";
    public static final String CantDeletePrimaryContact =  "CantDeletePrimaryContact";
    public static final String CustomerIdNotExists =  "CustomerIdNotExists";
    public static final String AddressExistsInDistrict = "AddressExistsInDistrict";
    public static final String CityNotExistsInDistrict = "CityNotExistsInDistrict";

    // ======================
    // City Validations
    // ======================
    public static final String CityNameNotBlank = "CityNameNotBlank";
    public static final String CityNamePattern = "CityNamePattern";
    public static final String CityNameExists = "CityNameExists";

    // ======================
    // Address Validations
    // ======================
    public static final String AddressStreetNotBlank = "AddressStreetNotBlank";
    public static final String AddressHouseNumberNotBlank = "AddressHouseNumberNotBlank";
    public static final String AddressDescriptionSize = "AddressDescriptionSize";

    // ======================
    // Billing Account Validations
    // ======================

    // ids
    public static final String BillingAccountCustomerIdNotNull = "BillingAccountCustomerIdNotNull";
    public static final String BillingAccountCustomerIdPositive = "BillingAccountCustomerIdPositive";
    public static final String BillingAccountAddressIdNotNull = "BillingAccountAddressIdNotNull";
    public static final String BillingAccountAddressIdPositive = "BillingAccountAddressIdPositive";

    // type
    public static final String BillingAccountTypeNotBlank = "BillingAccountTypeNotBlank";
    public static final String BillingAccountTypePattern = "BillingAccountTypePattern";

    // status
    public static final String BillingAccountStatusNotBlank = "BillingAccountStatusNotBlank";
    public static final String BillingAccountStatusPattern = "BillingAccountStatusPattern";

    // accountNumber
    public static final String BillingAccountAccountNumberNotBlank = "BillingAccountAccountNumberNotBlank";
    public static final String BillingAccountAccountNumberLength = "BillingAccountAccountNumberLength";
    public static final String BillingAccountAccountNumberPattern = "BillingAccountAccountNumberPattern";

    // accountName
    public static final String BillingAccountAccountNameNotBlank = "BillingAccountAccountNameNotBlank";
    public static final String BillingAccountAccountNameLength = "BillingAccountAccountNameLength";
    public static final String BillingAccountAccountNamePattern = "BillingAccountAccountNamePattern";

    // ======================
    // Contact Medium Validations
    // ======================
    public static final String ContactMediumCustomerIdNotNull = "ContactMediumCustomerIdNotNull";
    public static final String ContactMediumTypeNotBlank = "ContactMediumTypeNotBlank";
    public static final String ContactMediumTypePattern = "ContactMediumTypePattern";
    public static final String ContactMediumValueNotBlank = "ContactMediumValueNotBlank";
    public static final String ContactMediumValueLength = "ContactMediumValueLength";
    public static final String ContactMediumIsPrimaryNotNull = "ContactMediumIsPrimaryNotNull";

    // ======================
    // District Validations
    // ======================
    public static final String DistrictNameNotBlank = "DistrictNameNotBlank";
    public static final String DistrictNameLength = "DistrictNameLength";
    public static final String DistrictCityIdNotNull = "DistrictCityIdNotNull";

    // ======================
    // Individual Customer Validations
    // ======================
    public static final String IndividualCustomerFirstNameNotBlank = "IndividualCustomerFirstNameNotBlank";
    public static final String IndividualCustomerFirstNameLength = "IndividualCustomerFirstNameLength";
    public static final String IndividualCustomerNationalIdNotBlank = "IndividualCustomerNationalIdNotBlank";
    public static final String IndividualCustomerNationalIdLength = "IndividualCustomerNationalIdLength";
    public static final String IndividualCustomerNationalIdPattern = "IndividualCustomerNationalIdPattern";
}
