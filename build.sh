mvn clean install -f generateBillingItemFunc
[ $? -eq 0 ] || exit 1

mvn clean install -f generateInvoiceFunc
[ $? -eq 0 ] || exit 1

mvn clean install -f printInvoiceFunc
[ $? -eq 0 ] || exit 1