#write out current crontab
crontab -l > mycron
#echo new cron into cron file
echo "* * * * * echo hello" >> mycron
#install new cron file
crontab mycron
