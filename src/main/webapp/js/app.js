
var app = app || {};

app.wordsDataTable = null;
app.gameTablesDataTable = null;

app.login = function() {
    $.post('/login')
        .done(function() {
            app.refreshLoggedIn();
        })
        .fail(function(xhr, status, error) {
            console.log('login request failed');
            console.log(xhr);
            console.log(status);
            console.log(error);
        });
}

app.logout = function() {
    $.post('/logout')
        .done(function() {
            app.refreshLoggedIn();
        })
        .fail(function(xhr, status, error) {
            console.log('logout request failed');
            console.log(xhr);
            console.log(status);
            console.log(error);
        });
};

app.refreshLoggedIn = function() {
    $.get('/rest/user/simpleUser')
        .done(function(simpleUser) {
            console.log('authentication simpleUser');
            console.log(simpleUser);
            $('#user').text(simpleUser.name);
            $('#isAuthenticated').text(simpleUser.isAuthenticated ? 'Yes' : 'No');

            $('.authenticated').toggle(simpleUser.isAuthenticated);
            $('.unauthenticated').toggle(!simpleUser.isAuthenticated);
        })
        .fail(function(xhr, status, error) {
            console.log('simpleUser request failed');
            console.log(xhr);
            console.log(status);
            console.log(error);
        });
};

app.userRequest = function() {
    console.log('user request !');
    return $.get('/rest/user');
};

app.createWord = function(values) {
    console.log('create word request !');
    return $.ajax({
        url: '/rest/words',
        method: 'PUT',
        data: values
    })
};

app.deleteWord = function(id) {
    console.log('delete word ' + id + ' request !');
    $.ajax({
        url: '/rest/words/' + id,
        method: 'DELETE'
    })
    .done(function(data) {
        app.refreshWords();
    })
    .fail(function(xhr, status, error) {
        console.log('deleteWord request failed');
        console.log(xhr);
        console.log(status);
        console.log(error);
    });
};

app.listWords = function() {
    console.log('list words request !');
    return $.get('/rest/words');
};

app.showWords = function(data) {
    var data = data || [];
    // Initialize data table
    if(!app.wordsDataTable) {
        console.log('creating new data table');
        app.wordsDataTable = $('#wordsList').DataTable({
            pageLength: 10,
            columns: [
                { data: 'id' },
                { data: 'name' },
                {
                    data: 'wordCategory',
                    render: function(data, type, row, meta) {
                        switch(data) {
                            case 'NOUN':
                                return 'Podstatné jméno';
                            case 'VERB':
                                return 'Sloveso';
                            case 'ADJECTIVE':
                                return 'Přídavné jméno';
                            default:
                                return data;
                        }
                    }
                },
                {
                    data: null,
                    name: 'actions',
                    orderable: false,
                    searchable: false,
                    render: function(data, type, row, meta) {
                        return '<a class="button" href="javascript:app.deleteWord(' + row['id'] + ');">' + 'Delete' + '</a>';
                    }
                }
            ],
            data: data
        });
    } else {
        console.log('data table already exists');
        app.wordsDataTable.clear().rows.add(data).draw();
        console.log('retrieved data');
        console.log(data);
    }
};

app.refreshWords = function() {
    app.listWords().done(function(data) {
        app.showWords(data);
    })
    .fail(function(xhr, status, error) {
        console.log('listWords request failed');
        console.log(xhr);
        console.log(status);
        console.log(error);
    });
}

app.generateSentence = function(values) {
    console.log('generate sentence request !');
    return $.post('/rest/sentence', values);
};

app.deleteSentence = function(id) {
    console.log('delete sentence ' + id + ' request !');
    $.ajax({
        url: '/rest/sentence/' + id,
        type: 'DELETE'
    })
    .done(function(data) {
        app.refreshSentences();
    })
    .fail(function(xhr, status, error) {
        console.log('deleteSentence request failed');
        console.log(xhr);
        console.log(status);
        console.log(error);
    });
};

app.sentenceDetail = function(id) {
    console.log('sentence detail ' + id + ' request !');
    $.get('/rest/sentence/' + id)
    .done(function(data) {
        alert(
            'Sentence Detail:\n\n'
            + 'id: ' + data.id + '\n'
            + 'created: ' + data.created + '\n'
            + 'text: ' + data.text + '\n'
            + 'display count: ' + data.displayCount + '\n'
        );
    })
    .fail(function(xhr, status, error) {
        console.log('sentenceDetail request failed');
        console.log(xhr);
        console.log(status);
        console.log(error);
    });
}

app.sentenceDetailYodaTalk = function(id) {
    console.log('sentence detail yoda talk ' + id + ' request !');
    $.get('/rest/sentence/' + id + '/yodaTalk')
    .done(function(data) {
        alert(
            'Yoda Talk:\n\n'
            + 'text: ' + data.text + '\n'
        );
    })
    .fail(function(xhr, status, error) {
        console.log('sentenceDetailYodaTalk request failed');
        console.log(xhr);
        console.log(status);
        console.log(error);
    });
}

app.listSentences = function() {
    console.log('list sentences request !');
    return $.get('/rest/sentence');
};

app.showSentences = function(data) {
    var data = data || [];
    // Initialize data table
    if(!app.sentencesDataTable) {
        console.log('creating new data table');
        app.sentencesDataTable = $('#sentencesList').DataTable({
            pageLength: 10,
            columns: [
                { data: 'id' },
                { data: 'text' },
                { data: 'displayCount' },
                {
                    data: null,
                    name: 'actions',
                    orderable: false,
                    searchable: false,
                    render: function(data, type, row, meta) {
                        return '<a class="button" href="javascript:app.sentenceDetail(' + row['id'] + ');">' + 'View' + '</a>'
                        + ' <a class="button" href="javascript:app.sentenceDetailYodaTalk(' + row['id'] + ');">' + 'Yoda' + '</a>'
                        + ' <a class="button" href="javascript:app.deleteSentence(' + row['id'] + ');">' + 'Delete' + '</a>';
                    }
                }
            ],
            data: data
        });
    } else {
        console.log('data table already exists');
        app.sentencesDataTable.clear().rows.add(data).draw();
        console.log('retrieved data');
        console.log(data);
    }
};

app.refreshSentences = function() {
    app.listSentences().done(function(data) {
        app.showSentences(data);
    })
    .fail(function(xhr, status, error) {
        console.log('listSentences request failed');
        console.log(xhr);
        console.log(status);
        console.log(error);
    });
};

$(document).ready(function() {

    app.refreshLoggedIn();
    app.refreshWords();
    app.refreshSentences();

    $('#userRequest').on('click', function() {
        app.userRequest().done(function(data) {
            console.log('user request completed');
            console.log(data);
            alert('look into console!');
        })
        .fail(function(xhr, status, error) {
            console.log('user request failed');
            console.log(xhr);
            console.log(status);
            console.log(error);
            alert('Request failed: ' + status + ' ' + error);
        });
    });

    $('#createWord').closest('form').on('submit', function(e) {
        e.preventDefault();
        // Check for empty
        var name = $(this).find('input[name="name"]');
        if(!name.val()) {
            name.focus();
            return;
        }
        // Send to server
        var values = $(this).serializeArray();
        console.log('Serialized values');
        console.log(values);
        app.createWord(values).done(function(data) {
            console.log('word created');
            console.log(data);
            $("#createWordForm").trigger("reset");
            app.refreshWords();
        })
        .fail(function(xhr, status, error) {
            console.log('create word request failed');
            console.log(xhr);
            console.log(status);
            console.log(error);
            alert('Request failed: ' + status + ' ' + (xhr.responseJSON.error ? xhr.responseJSON.error : error));
        });
    });

    $('#generateSentence').on('click', function() {
        app.generateSentence().done(function(data) {
            console.log('sentence generated');
            console.log(data);
            app.refreshSentences();
        })
        .fail(function(xhr, status, error) {
            console.log('generate sentence request failed');
            console.log(xhr);
            console.log(status);
            console.log(error);
            alert('Request failed: ' + status + ' ' + (xhr.responseJSON.error ? xhr.responseJSON.error : error));
        });
    });
});
